package com.yuen.service;

import com.yuen.domain.Post;
import com.yuen.domain.User;
import com.yuen.domain.Notification;

import java.util.List;

public interface NotificationService {

	List<Notification> findNotRead(User user);
	
	void pushFollowNotification(User sender, User receiver);
	
	void pushLikeNotification(User sender, User receiver, Post post);
	
	void pushCommentNotification(User sender, User receiver, Post post);
	
	void makeRead(User user);
	
}
