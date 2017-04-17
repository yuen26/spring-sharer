package com.yuen.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuen.domain.Notification;
import com.yuen.domain.Post;
import com.yuen.domain.User;
import com.yuen.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Notification> findNotRead(User user) {
		return notificationRepository.findNotRead(user);
	}
	
	@Override
	@Transactional
	public void pushFollowNotification(User sender, User receiver) {
		// Init notification
		Notification notification = new Notification();
		
		StringBuilder content = new StringBuilder();
		content.append(sender.getFullname());
		content.append(" đã theo dõi bạn");
		notification.setContent(content.toString());
		
		StringBuilder redirect = new StringBuilder();
		redirect.append("/user/");
		redirect.append(sender.getUsername());
		notification.setRedirect(redirect.toString());
		
		notification.setCreated(new Date());
		notification.setRead(false);
		notification.setSender(sender);
		notification.setReceiver(receiver);
		
		// Save notification
		notificationRepository.save(notification);
	}
	
	@Override
	@Transactional
	public void pushLikeNotification(User sender, User receiver, Post post) {
		// Init notification
		Notification notification = new Notification();
		
		StringBuilder content = new StringBuilder();
		content.append(sender.getFullname());
		content.append(" đã thích bài viết ");
		content.append(post.getCaption());
		content.append(" của bạn");
		notification.setContent(content.toString());
		
		StringBuilder redirect = new StringBuilder();
		redirect.append("/post/");
		redirect.append(post.getId());
		notification.setRedirect(redirect.toString());
		
		notification.setCreated(new Date());
		notification.setRead(false);
		notification.setSender(sender);
		notification.setReceiver(receiver);
		
		// Save notification
		notificationRepository.save(notification);
	}
	
	@Override
	@Transactional
	public void pushCommentNotification(User sender, User receiver, Post post) {
		// Init notification
		Notification notification = new Notification();
		
		StringBuilder content = new StringBuilder();
		content.append(sender.getFullname());
		content.append(" đã bình luận bài viết ");
		content.append(post.getCaption());
		content.append(" của bạn");
		notification.setContent(content.toString());
		
		StringBuilder redirect = new StringBuilder();
		redirect.append("/post/");
		redirect.append(post.getId());
		notification.setRedirect(redirect.toString());
		
		notification.setCreated(new Date());
		notification.setRead(false);
		notification.setSender(sender);
		notification.setReceiver(receiver);
		
		// Save notification
		notificationRepository.save(notification);
	}
	
	@Override
	@Transactional
	public void makeRead(User user) {
		notificationRepository.makeRead(user);
	}

}
