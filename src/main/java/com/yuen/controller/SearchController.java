package com.yuen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuen.domain.CustomUserDetails;
import com.yuen.domain.User;
import com.yuen.service.UserService;

@Controller
public class SearchController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/search")
	public String search(@RequestParam("q") String q, Model model) {
		if (q.isEmpty()) {
			return "redirect:/";
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = ((CustomUserDetails) authentication.getPrincipal()).getUser();
		List<User> users = userService.search(q);
		users.remove(currentUser); // remove current user from search results
		
		model.addAttribute("users", users);
		return "search";
	}
	
}
