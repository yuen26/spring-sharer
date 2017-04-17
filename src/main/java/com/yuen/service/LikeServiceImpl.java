package com.yuen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuen.domain.Like;
import com.yuen.domain.Post;
import com.yuen.domain.User;
import com.yuen.repository.LikeRepository;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	private LikeRepository likeRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Like liked(Post post, User user) {
		return likeRepository.findByPostAndUser(post, user);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int countByPost(Post post) {
		return likeRepository.countByPost(post);
	}
	
	@Override
	@Transactional
	public Like save(Like like) {
		return likeRepository.save(like);
	}
	
	@Override
	@Transactional
	public void delete(Like like) {
		likeRepository.delete(like);
	}

}
