package com.yuen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuen.domain.Comment;
import com.yuen.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	@Transactional
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

}
