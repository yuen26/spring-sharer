package com.yuen.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yuen.domain.User;
import com.yuen.service.UserService;
import com.yuen.validator.RegisterValidator;

@Controller
public class RegisterController {

	@Autowired
	private RegisterValidator registerValidator;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String getRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/register")
	public String postRegister(@Valid User user, BindingResult result, RedirectAttributes redirect) {
		registerValidator.validate(user, result);
		if (result.hasErrors()) {
			return "register";
		}

		userService.register(user);
		
		redirect.addFlashAttribute("success", "Đăng ký thành công!");
		return "redirect:/login?register";
	}
	
}
