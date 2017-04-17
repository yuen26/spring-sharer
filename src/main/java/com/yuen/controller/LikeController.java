package com.yuen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuen.domain.CustomUserDetails;
import com.yuen.domain.Like;
import com.yuen.domain.Post;
import com.yuen.domain.User;
import com.yuen.service.LikeService;
import com.yuen.service.NotificationService;
import com.yuen.service.PostService;

@Controller
public class LikeController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/like")
	@ResponseBody
	public int like(@RequestParam int postId) {
		// Fetch post and current user
		Post post = postService.findOne(postId);
		CustomUserDetails principal = 
    			(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = principal.getUser();
		
		Like liked = likeService.liked(post, currentUser);
    	if (liked == null) {
    		// Insert new like into DB
    		likeService.save(new Like(post, currentUser));
    		
    		// Push notification
    		notificationService.pushLikeNotification(currentUser, post.getUser(), post);
    	}
    	
    	// Count new total likes of post 
    	int likes = likeService.countByPost(post);
    	
		return likes;
	}
	
	@GetMapping("/unlike")
	@ResponseBody
	public int unlike(@RequestParam int postId) {
		// Fetch post and current user
		Post post = postService.findOne(postId);
		Object principal = 
    			(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User currentUser = ((CustomUserDetails) principal).getUser();
		
		Like liked = likeService.liked(post, currentUser);
    	if (liked != null) {
    		// Delete like from DB
    		likeService.delete(liked);
    	}
    	
    	// Count new total likes of post 
    	int likes = likeService.countByPost(post);
    	
		return likes;
	}
	
}
