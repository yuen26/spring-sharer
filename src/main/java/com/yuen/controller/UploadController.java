package com.yuen.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuen.domain.CustomUserDetails;
import com.yuen.domain.Post;
import com.yuen.domain.User;
import com.yuen.service.PostService;

@Controller
public class UploadController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/upload/photo")
	public String uploadPhoto() {
		return "upload_photo";
	}
	
	@GetMapping("/upload/video")
	public String uploadVideo() {
		return "upload_video";
	}
	
	@PostMapping("/upload")
    @ResponseBody
    public String upload(
    		@RequestParam("type") String type,
    		@RequestParam("caption") String caption, 
    		@RequestParam("file") MultipartFile multipartFile) {
        try {
        	// Save post
        	Post post = new Post();
        	post.setType(type);
        	post.setCaption(caption);
        	post.setCreated(new Date());
        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        	post.setUser(currentUser);
        	postService.save(post, multipartFile);
        	
        	return "success";
        } catch (IOException e) {
        	return e.getMessage();
        }
    }
	
}
