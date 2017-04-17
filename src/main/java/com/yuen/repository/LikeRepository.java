package com.yuen.repository;

import org.springframework.data.repository.CrudRepository;

import com.yuen.domain.Like;
import com.yuen.domain.Post;
import com.yuen.domain.User;

public interface LikeRepository extends CrudRepository<Like, Integer> {

	Like findByPostAndUser(Post post, User user);
	
	Integer countByPost(Post post);
	
}
