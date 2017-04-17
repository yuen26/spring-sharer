package com.yuen.service;

import com.yuen.domain.User;

public interface FollowService {

	void follow(User follower, User followed);
	
	void unfollow(User follower, User followed);
	
}
