package com.yuen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yuen.domain.Notification;
import com.yuen.domain.User;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

	@Query("select n from Notification n left join fetch n.sender where n.receiver = ?1 and n.read = 0 order by n.created desc")
	List<Notification> findNotRead(User user);

	@Modifying
	@Query("update Notification n set n.read = 1 where n.receiver = ?1 and n.read = 0")
	void makeRead(User user);
	
}
