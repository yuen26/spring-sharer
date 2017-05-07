package com.yuen.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yuen.domain.User;

public interface UserService {
	
	List<User> search(String q);
	
	User findById(int id);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User save(User user);
	
	void delete(User user);
	
	User changeAvatar(User user, MultipartFile multipartFile) throws IOException; 
	
	boolean isFollowing(User user1, User user2);

}