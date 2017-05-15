package com.yuen.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@NotEmpty
	@Length(max = 100)
	@Column(name = "fullname", nullable = false, length = 100)
	private String fullname;

	@NotEmpty
	@Pattern(regexp = "^[A-Za-z0-9_]+$")
	@Column(name = "username", nullable = false, length = 50)
	private String username;
	
	@NotEmpty
	@Email
	@Column(name="email", nullable = false, length = 255)
	private String email;
	
	@NotEmpty
	@Length(min = 8)
	@Column(name = "password", nullable = false, length = 60)
	private String password;

	@Transient
	private String confirmPassword;
	
	@Column(name = "avatar", nullable = false, length = 255)
	private String avatar;
	
	@Column(name = "facebook_id", nullable = true, length = 16)
	private String facebookId;
	
	@Column(name = "folder_id", nullable = true, length = 50)
	private String folderId;
	
	@Column(name = "is_locked", nullable = false)
	private boolean isLocked;
	
	@JoinTable(
		name = "user_role", 
		joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, 
		inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
	)
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();
	
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Post> posts = new LinkedHashSet<>();
	
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
	
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Like> likes = new HashSet<>();
	
    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Notification> senderNotifications = new HashSet<>();
    
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Notification> receiverNotifications = new HashSet<>();
    
    @JoinTable(
			name = "relationship", 
			joinColumns = {@JoinColumn(name = "follower", referencedColumnName = "id")}, 
			inverseJoinColumns = {@JoinColumn(name = "followed", referencedColumnName = "id")}
	)
    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<User> followings = new HashSet<>();

	@ManyToMany(mappedBy = "followings")
    private Set<User> followers = new HashSet<>();
    
	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getFacebookId() {
		return this.facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	
	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void addRole(Role role) {
		roles.add(role);
		role.getUsers().add(this);
	}
	
	public void removeRole(Role role) {
		roles.remove(role);
		role.getUsers().remove(this);
	}
	
	public Set<Post> getPosts() {
		return this.posts;
	}

	public void addPost(Post post) {
		posts.add(post);
		post.setUser(this);
	}
	
	public void removePost(Post post) {
		posts.remove(post);
		post.setUser(null);
	}
	
	public Set<Comment> getComments() {
		return this.comments;
	}

	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setUser(this);
	}
	
	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setUser(null);
	}
	
	public Set<Like> getLikes() {
		return this.likes;
	}

	public void addLike(Like like) {
		likes.add(like);
		like.setUser(this);
	}
	
	public void removeLike(Like like) {
		likes.remove(like);
		like.setUser(null);
	}
	
	public Set<Notification> getSenderNotifications() {
		return senderNotifications;
	}

	public void addSenderNotification(Notification notification) {
		senderNotifications.add(notification);
		notification.setSender(this);
	}
	
	public void removeSenderNotification(Notification notification) {
		senderNotifications.remove(notification);
		notification.setSender(null);
	}
	
	public Set<Notification> getReceiverNotifications() {
		return receiverNotifications;
	}

	public void addReceivererNotification(Notification notification) {
		receiverNotifications.add(notification);
		notification.setSender(this);
	}
	
	public void removeReceiverNotification(Notification notification) {
		receiverNotifications.remove(notification);
		notification.setSender(null);
	}
	
	public Set<User> getFollowings() {
		return followings;
	}

	public void addFollowing(User following) {
		followings.add(following);
		following.getFollowers().add(this);
	}
	
	public void removeFollowing(User following) {
		followings.remove(following);
		following.getFollowers().remove(this);
	}
	
	// One connection includes current user and him/her followings
	public Set<User> getConnection() {
		Set<User> connection = new HashSet<>();
		connection.add(this);
		connection.addAll(followings);
		return connection;
	}
	
	public Set<User> getFollowers() {
		return followers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}