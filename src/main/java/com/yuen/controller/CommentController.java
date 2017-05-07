package com.yuen.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yuen.domain.Comment;
import com.yuen.domain.CustomUserDetails;
import com.yuen.domain.Post;
import com.yuen.domain.User;
import com.yuen.service.CommentService;
import com.yuen.service.NotificationService;
import com.yuen.service.PostService;

@Controller
public class CommentController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private NotificationService notificationService;
	
	@PostMapping("/comment")
    public ModelAndView comment(@RequestParam String content, @RequestParam int postId, Model model) {
		// Fetch post and current user
		Post post = postService.findOne(postId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        
        // Save comment
		Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreated(new Date());
        comment.setPost(post);
    	comment.setUser(currentUser);
    	commentService.save(comment);
    	
    	// Push notification
    	if (!currentUser.equals(post.getUser())) {
    		notificationService.pushCommentNotification(currentUser, post.getUser(), post);
    	}
    	
        model.addAttribute("comment", comment);
        return new ModelAndView("comment_fragment");
    }
	
}
