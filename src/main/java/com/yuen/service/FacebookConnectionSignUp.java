package com.yuen.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuen.repository.UserRepository;
import com.yuen.util.FacebookUtil;

@Service
public class FacebookConnectionSignUp implements ConnectionSignUp {
 
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public String execute(Connection<?> connection) {
		Facebook facebook = (Facebook) connection.getApi();
		User fbUser = FacebookUtil.getUser(facebook);
		
		com.yuen.domain.User dbUser = userRepository.findByEmail(fbUser.getEmail());
		if (dbUser != null) {
			dbUser.setFacebookId(fbUser.getId());
			userRepository.save(dbUser);
		} else {
			dbUser = new com.yuen.domain.User();
			dbUser.setFullname(fbUser.getName());
			dbUser.setUsername(fbUser.getId());
	        dbUser.setEmail(fbUser.getEmail());
	        dbUser.setPassword(passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(8)));
	        dbUser.setAvatar(connection.createData().getImageUrl());
	        dbUser.setFacebookId(fbUser.getId());
	        userRepository.save(dbUser);
		}
		
        return fbUser.getEmail();
	}
 
}
