package uk.ac.york.eng2.videos.domain;

import java.util.Set;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Video {
	private Long id;
	private String user;
	private String title;
	private String hashtags;
	
	private Set<User> viewers;
	private Set<User> likes;
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

	@Override
	public String toString() {
		return "Video [id=" + id + ", user=" + user + ", title=" + title + ", hashtags=" + hashtags + "]";
	}
}
