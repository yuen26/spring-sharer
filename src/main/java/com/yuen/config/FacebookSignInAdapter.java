package com.yuen.config;


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.web.context.request.NativeWebRequest;

import com.yuen.domain.CustomUserDetails;
import com.yuen.util.FacebookUtil;

@Configurable
public class FacebookSignInAdapter implements SignInAdapter {
	
	private UserDetailsService userDetailsService;
	
	public FacebookSignInAdapter(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
		Facebook facebook = (Facebook) connection.getApi();
		User fbUser = FacebookUtil.getUser(facebook);
		
		CustomUserDetails userDetails = 
				(CustomUserDetails) userDetailsService.loadUserByUsername(fbUser.getEmail());
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(
						userDetails, 
						userDetails.getPassword(), 
						userDetails.getAuthorities()));

		return null;
	}

}
