package com.yuen.service;

import com.yuen.domain.Like;
import com.yuen.domain.Post;
import com.yuen.domain.User;

public interface LikeService {

	Like liked(Post post, User user);
	
	int countByPost(Post post);
	
	Like save(Like like);
	
	void delete(Like like);
	
}
