package com.yuen.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GoogleDriveUtil {

	/** Application name. */
	private static final String APPLICATION_NAME = "Sharer";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/sharer");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/drive-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE_FILE);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public static Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = GoogleDriveUtil.class.getResourceAsStream("/client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	/**
	 * Build and return an authorized Drive client service.
	 * 
	 * @return an authorized Drive client service
	 * @throws IOException
	 */
	public static Drive getDriveService() throws IOException {
		Credential credential = authorize();
		return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
	}

	/**
	 * Create GoogleDrive nested folder
	 * https://developers.google.com/drive/v3/web/folder
	 * 
	 * @param String 
	 * 			Folder name
	 * @return com.google.api.services.drive.model.File
	 * @throws IOException
	 */
	public static File createFolder(String name) {
		File fileMetadata = new File();
		fileMetadata.setName(name);
		fileMetadata.setParents(Collections.singletonList(Const.DRIVE_FOLDER_ID));
		fileMetadata.setMimeType("application/vnd.google-apps.folder");

		File file = null;
		try {
			file = getDriveService().files().create(fileMetadata)
			        .setFields("id")
			        .execute();
			System.out.println(file.getId());
		} catch (IOException e) {
			System.out.println("An error occurred: " + e);
		}
		
		return file;
	}
	
	/**
	 * Upload photo or video to GoogleDrive folder
	 * https://developers.google.com/drive/v3/web/folder
	 * 
	 * @return com.google.api.services.drive.model.File
	 * @throws IOException
	 */
	public static File upload(java.io.File filePath, String type, String parent) throws IOException {
		File fileMetadata = new File();
		fileMetadata.setName(filePath.getName());
		fileMetadata.setParents(Collections.singletonList(parent));
		FileContent mediaContent = null;
		switch (type) {
			case "photo":
				mediaContent = new FileContent("image/jpeg", filePath);
				break;
			case "video":
				mediaContent = new FileContent("video/*", filePath);
				break;
		}
		File file = getDriveService().files().create(fileMetadata, mediaContent).setFields("id, parents").execute();
		System.out.println(file.getId());

		return file;
	}

	/**
	 * Permanently delete a file or folder, skipping the trash.
	 *
	 * @param service
	 *            Drive API service instance.
	 * @param fileId
	 *            ID of the file or folder to delete.
	 */
	public static void deleteFile(String fileId) {
		try {
			getDriveService().files().delete(fileId).execute();
		} catch (IOException e) {
			System.out.println("An error occurred: " + e);
		}
	}

	/**
	 * Create photo url after uploading
	 * 
	 * @return String
	 */
	public static String createPhotoUrl(String fileId) {
		return "https://drive.google.com/uc?export=download&id=" + fileId;
	}

	/**
	 * Create video iframe url after uploading
	 * 
	 * @return String
	 */
	public static String createVideoUrl(String fileId) {
		return "https://drive.google.com/file/d/" + fileId + "/preview";
	}

}