package com.yuen.util;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;

public class FacebookUtil {

	public static User getUser(Facebook facebook) {
		String [] fields = {"email", "id", "name"};
		return facebook.fetchObject("me", User.class, fields);
	}
	
}

