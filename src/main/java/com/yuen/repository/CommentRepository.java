package com.yuen.repository;

import org.springframework.data.repository.CrudRepository;

import com.yuen.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

}
