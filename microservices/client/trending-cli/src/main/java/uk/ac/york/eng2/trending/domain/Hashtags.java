package uk.ac.york.eng2.trending.domain;

import java.time.Instant;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Hashtags {

    private Long id;

    private Instant created;
    private String user;
    private String hashtags;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

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

	@Override
	public String toString() {
		return "Hashtags [id=" + id + ", created=" + created + ", user=" + user + ", hashtags=" + hashtags + "]";
	}
	
}
