package com.yuen.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.yuen.domain.Post;
import com.yuen.domain.User;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
	
	// Must have countQuery when join fetch with pagination
	@Query(value = "select p from Post p left join fetch p.likes left join fetch p.comments left join fetch p.user where p.user = ?1",
			countQuery = "select count(p) from Post p where p.user = ?1")
	Page<Post> findByUser(User user, Pageable pageable);
	
	// Must have countQuery when join fetch with pagination
	@Query(value = "select p from Post p left join fetch p.likes left join fetch p.comments left join fetch p.user where p.user in ?1",
			countQuery = "select count(p) from Post p where p.user in ?1")
	Page<Post> findByUserIn(Set<User> users, Pageable pageable);
	
	@Query(value = "select p from Post p left join fetch p.likes left join fetch p.comments left join fetch p.user where p.id = ?1")
	Post findOne(Integer id);
	
	Integer countByUser(User user);
	
}
