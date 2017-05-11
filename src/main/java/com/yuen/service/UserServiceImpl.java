package com.yuen.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.model.File;
import com.yuen.domain.User;
import com.yuen.repository.RoleRepository;
import com.yuen.repository.UserRepository;
import com.yuen.util.Const;
import com.yuen.util.FileUtil;
import com.yuen.util.GoogleDriveUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(readOnly = true)
	public List<User> search(String q) {
		List<User> users = userRepository.findByFullnameContaining(q);
		return users;
	}
	
	@Override
	@Transactional(readOnly = true)
	public User findById(int id) {
		return userRepository.findOne(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public User save(User user) {
		// Insert into DB
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setAvatar(GoogleDriveUtil.createPhotoUrl(Const.DEFAULT_AVATAR_ID));
		user.addRole(roleRepository.findByName("ROLE_MEMBER"));
		user = userRepository.save(user);
		
		// Create GG Drive folder for user
		File folder = GoogleDriveUtil.createFolder(String.valueOf(user.getId()));
		user.setFolderId(folder.getId());
		userRepository.save(user);
		
		return user;
	}

	@Override
	public void delete(User user) {
		// Delete user in DB
		userRepository.delete(user);
		
		// Delete Google Drive folder
		GoogleDriveUtil.deleteFile(user.getFolderId());
	}
	
	@Override
	@Transactional
	public User changeAvatar(User user, MultipartFile multipartFile) throws IOException {
		// Upload file
		File uploadedFile = GoogleDriveUtil.upload(
				FileUtil.convert(multipartFile), "photo", user.getFolderId());
				
		// Update avatar attribute
		user.setAvatar(GoogleDriveUtil.createPhotoUrl(uploadedFile.getId()));
    	return userRepository.save(user);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean isFollowing(User user1, User user2) {
		return (userRepository.findByIdAndFollowings_Id(user1.getId(), user2.getId()) != null) ? true : false; 
	}

}
