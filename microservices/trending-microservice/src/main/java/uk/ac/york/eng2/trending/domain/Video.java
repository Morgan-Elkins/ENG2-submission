package uk.ac.york.eng2.trending.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micronaut.serde.annotation.Serdeable;

@Entity
@Serdeable
public class Video {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String user;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String hashtags;
	
	@JsonIgnore
	@ManyToMany
	private Set<User> viewers;
	
	@JsonIgnore
	@JoinTable(name = "likedVideos")
	@ManyToMany
	private Set<User> likes;
	
	@JsonIgnore
	@JoinTable(name = "dislikedVideos")
	@ManyToMany
	private Set<User> dislikes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHashtags() {
		return hashtags;
	}

	public void setHashtags(String hashtags) {
		this.hashtags = hashtags;
	}

	public Set<User> getViewers() {
		return viewers;
	}

	public void setViewers(Set<User> viewers) {
		this.viewers = viewers;
	}

	public Set<User> getLikes() {
		return likes;
	}

	public void setLikes(Set<User> likes) {
		this.likes = likes;
	}

	public Set<User> getDislikes() {
		return dislikes;
	}

	public void setDislikes(Set<User> dislikes) {
		this.dislikes = dislikes;
	}
	
	
}
