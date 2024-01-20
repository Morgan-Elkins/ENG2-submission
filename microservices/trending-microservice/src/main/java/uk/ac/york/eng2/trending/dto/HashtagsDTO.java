package uk.ac.york.eng2.trending.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class HashtagsDTO {
	
	private String user;
    private String hashtags;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getHashtags() {
		return hashtags;
	}
	public void setHashtags(String hashtags) {
		this.hashtags = hashtags;
	}
    
    
}
