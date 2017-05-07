package com.yuen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yuen.domain.User;
import com.yuen.service.UserService;

@Controller
public class UserManagerController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/admin/user/{id}/lock")
	public String lock(@PathVariable("id") Integer id, RedirectAttributes redirect) {
		User user = userService.findById(id);
		
		if (user == null) {
		    return "404";
		}
		
		user.setLocked(true);
		userService.save(user);
		
		redirect.addFlashAttribute("message", "Khóa tài khoản " + user.getUsername() + " thành công!");
	    return "redirect:/";
	}
	
	@GetMapping("/admin/user/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirect) {
		User user = userService.findById(id);
		
		if (user == null) {
		    return "404";
		}
		
		userService.delete(user);
		
		redirect.addFlashAttribute("message", "Xóa tài khoản " + user.getUsername() + " thành công!");
	    return "redirect:/";
	}
	
}
