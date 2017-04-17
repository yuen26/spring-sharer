package com.yuen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuen.domain.User;
import com.yuen.repository.UserRepository;

@Service
public class FollowServiceImpl implements FollowService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional
	public void follow(User follower, User followed) {
		follower.addFollowing(followed);
		userRepository.insertFollowing(follower.getId(), followed.getId());
	}

	@Override
	@Transactional
	public void unfollow(User follower, User followed) {
		follower.removeFollowing(followed);
		userRepository.deleteFollowing(follower.getId(), followed.getId());
	}

}
