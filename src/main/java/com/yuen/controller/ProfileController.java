package com.yuen.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yuen.domain.CustomUserDetails;
import com.yuen.domain.Post;
import com.yuen.domain.User;
import com.yuen.service.PostService;
import com.yuen.service.UserService;
import com.yuen.util.Const;
import com.yuen.validator.ChangeProfileValidator;

@Controller
@SessionAttributes("user")
public class ProfileController {
	
	@Autowired
	private ChangeProfileValidator changeProfileValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/user/{username}")
	public String index(@PathVariable("username") String username, Model model) throws IOException {
		// Get querying user and his/her posts
		User user = userService.findByUsername(username);
		Page<Post> posts = postService.findByUser(user, 0, Const.POSTS_PER_PAGE);
		model.addAttribute("user", user);
		if (posts.hasContent()) {
			model.addAttribute("posts", posts);
		}

		// Get current user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = ((CustomUserDetails) authentication.getPrincipal()).getUser();
		
		// Check current user is following query user or not
		if (!user.equals(currentUser)) {
			model.addAttribute("isFollowing", userService.isFollowing(currentUser, user));
		}

		return "profile";
	}
	
	@GetMapping("/user/more")
    public ModelAndView loadMore(@RequestParam int userId, @RequestParam int page, Model model) {
        User user = userService.findById(userId);
        Page<Post> posts = postService.findByUser(user, page, Const.POSTS_PER_PAGE);
        
        if (posts.hasContent()) {
			model.addAttribute("posts", posts);
		}
        return new ModelAndView("profile_fragment");
    }
	
	@InitBinder
	protected void allowFields(WebDataBinder binder) {
		binder.setAllowedFields("fullname", "username", "password", "confirmPassword");
	}
	
	@GetMapping("/user/change-profile")
	public String getChangeProfile(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = ((CustomUserDetails) authentication.getPrincipal()).getUser();
		
		model.addAttribute("user", currentUser);
		return "change_profile";
	}
	
	@PostMapping("/user/change-profile")
	public String postChangeProfile(@Valid User user, BindingResult result, SessionStatus sessionStatus) {
		changeProfileValidator.validate(user, result);
		if (result.hasErrors()) {
			return "change_profile";
		}
		
		userService.save(user);
		sessionStatus.setComplete();
		
		return "redirect:/login?logout";
	}
	
	@GetMapping("/user/change-avatar")
	public String getChangeAvatar() {
		return "change_avatar";
	}
	
	@PostMapping("/user/change-avatar")
    public String postChangeAvatar(@RequestParam("avatar") MultipartFile multipartFile) {
        try {
        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        	userService.changeAvatar(currentUser, multipartFile);
        	
        	return "redirect:/user/" + currentUser.getUsername();
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        	return "change_avatar";
        }
    }
	
}
