package com.yuen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuen.domain.CustomUserDetails;
import com.yuen.domain.User;
import com.yuen.service.NotificationService;

@Controller
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/notification/make-read")
	@ResponseBody
	public String makeRead() {
		CustomUserDetails principal = 
    			(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = principal.getUser();
		notificationService.makeRead(currentUser);
		
		return "success";
	}
	
}
