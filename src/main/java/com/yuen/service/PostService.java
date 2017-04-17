package com.yuen.service;

import java.io.IOException;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.yuen.domain.Post;
import com.yuen.domain.User;

public interface PostService {

	Page<Post> findByUser(User user, int page, int size);
	
	Page<Post> findByUsers(Set<User> user, int page, int size);
	
	Post findOne(int id);
	
	Post save(Post post);
	
	Post save(Post post, MultipartFile multipartFile) throws IOException;
	
	int countByUser(User user);
	
	void delete(Post post);
	
}
