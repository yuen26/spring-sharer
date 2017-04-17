package com.yuen.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.yuen.domain.CustomUserDetails;
import com.yuen.domain.Post;
import com.yuen.domain.User;
import com.yuen.service.LikeService;
import com.yuen.service.PostService;
import com.yuen.service.UserService;

@Controller
@SessionAttributes("post")
public class PostController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private LikeService likeService;
	
	@GetMapping("/post/{id}")
	public String index(@PathVariable("id") int id, Model model) {
		Post post = postService.findOne(id);
		
		if (post == null) {
			return "404";
		}
		
		User owner = post.getUser();
		CustomUserDetails principal = 
    			(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = principal.getUser();
    	
		// Check current user liked post not
		boolean isLiked = (likeService.liked(post, currentUser) != null) ? true : false;
		
		// Check current user is following post owner not
		if (!owner.equals(currentUser)) {
			model.addAttribute("isFollowing", userService.isFollowing(currentUser, owner));
		}
		
		model.addAttribute("post", post);
		model.addAttribute("isLiked", isLiked);
		return "post";
	}
	
	@InitBinder
	protected void allowFields(WebDataBinder binder) {
		binder.setAllowedFields("caption");
	}
	
	@GetMapping("/post/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		Post post = postService.findOne(id);
		
		if (post == null) {
			return "404";
		} 
		
		// Check current user is owner post not to edit
		CustomUserDetails principal = 
    			(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = principal.getUser();
		if (!post.getUser().equals(currentUser)) {
			return "404";
		}
		
		model.addAttribute("post", post);
		return "post_edit";
	}
	
	@PostMapping("/post/update")
	public String update(@Valid Post post, BindingResult result, SessionStatus sessionStatus) {
		if (result.hasErrors()) {
			return "post_edit";
		}
		
		postService.save(post);
		sessionStatus.setComplete();
		
		return "redirect:/post/" + post.getId();
	}
	
	@GetMapping("/post/{id}/delete")
	public String delete(@PathVariable("id") int id) {
		Post post = postService.findOne(id);
		
		if (post == null) {
			return "404";
		}
		
		// Check current user is owner post not to delete
		CustomUserDetails principal = 
    			(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = principal.getUser();
		if (!post.getUser().equals(currentUser)) {
			return "404";
		}
		
		postService.delete(post);
		
		return "redirect:/";
	}
	
}
