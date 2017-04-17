package com.yuen.service;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.model.File;
import com.yuen.domain.Post;
import com.yuen.domain.User;
import com.yuen.repository.PostRepository;
import com.yuen.util.FileUtil;
import com.yuen.util.GoogleDriveUtil;

@Service
@Transactional
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Page<Post> findByUser(User user, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, "created");
		return postRepository.findByUser(user, pageRequest);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Post> findByUsers(Set<User> users, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, "created");
		return postRepository.findByUserIn(users, pageRequest);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Post findOne(int id) {
		return postRepository.findOne(id);
	}
	
	@Override
	@Transactional
	public Post save(Post post) {
		return postRepository.save(post);
	}
	
	@Override
	@Transactional
	public Post save(Post post, MultipartFile multipartFile) throws IOException {
		// Upload file
		File uploadedFile = GoogleDriveUtil.upload(FileUtil.convert(multipartFile), post.getType());
		
		// Set Google Drive file ID of post
		post.setFileId(uploadedFile.getId());
		
		// Set Google Drive URL of post
    	if (post.getType().equals("image")) {
    		post.setUrl(GoogleDriveUtil.createImageUrl(uploadedFile.getId()));
    	} else {
    		post.setUrl(GoogleDriveUtil.createVideoUrl(uploadedFile.getId()));
    	}
    	
    	return postRepository.save(post);
	}

	@Override
	@Transactional
	public int countByUser(User user) {
		return postRepository.countByUser(user);
	}

	@Override
	@Transactional
	public void delete(Post post) {
		// Delete file in Google Drive
		GoogleDriveUtil.deleteFile(post.getFileId());
		
		// Delete post in DB
		postRepository.delete(post);
	}
	
}
