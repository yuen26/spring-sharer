package com.yuen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yuen.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u.followings from User u where u = ?1")
	List<User> findFollowings(User user);
	
	List<User> findByFullnameContaining(String q);
	
	@Query("select u from User u left join fetch u.followings left join fetch u.followers where u.username = ?1")
	User findByUsername(String username);
	
	@Query("select u from User u left join fetch u.followings where u.email = ?1")
	User findByEmail(String email);
	
	User findByIdAndFollowings_Id(int followerId, int followedId);
	
	@Modifying
	@Query(value = "INSERT INTO relationship(follower, followed) VALUES (?1, ?2)", nativeQuery = true)
	void insertFollowing(int followerId, int followedId);
	
	@Modifying
	@Query(value = "DELETE FROM relationship WHERE follower = ?1 AND followed = ?2", nativeQuery = true)
	void deleteFollowing(int followerId, int followedId);
	
}
