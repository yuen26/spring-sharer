package com.yuen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuen.domain.CustomUserDetails;
import com.yuen.domain.User;
import com.yuen.service.FollowService;
import com.yuen.service.NotificationService;
import com.yuen.service.UserService;

@Controller
public class FollowController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/follow")
	@ResponseBody
	public String follow(@RequestParam int userId) {
		// Insert new relationship into DB
		User user = userService.findById(userId);
		CustomUserDetails principal = 
    			(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = principal.getUser();
		followService.follow(currentUser, user);
		
		// Push notification
    	notificationService.pushFollowNotification(currentUser, user);
		
		// Update principal
		currentUser.addFollowing(user);
		principal.setUser(currentUser);
		Authentication authentication = 
				new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "success";
	}
	
	@GetMapping("/unfollow")
	@ResponseBody
	public String unfollow(@RequestParam int userId) {
		// Delete relationship from DB
		User user = userService.findById(userId);
		CustomUserDetails principal = 
    			(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = principal.getUser();
		followService.unfollow(currentUser, user);
		
		// Update principal
		currentUser.removeFollowing(user);
		principal.setUser(currentUser);
		Authentication authentication = 
				new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "success";
	}
	
}
