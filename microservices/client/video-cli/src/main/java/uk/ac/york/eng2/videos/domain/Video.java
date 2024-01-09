package uk.ac.york.eng2.videos.domain;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Video {
	private Long id;
	private String user;
	private String title;
	private String hashtags;

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

	@Override
	public String toString() {
		return "Video [id=" + id + ", user=" + user + ", title=" + title + ", hashtags=" + hashtags + "]";
	}
}
