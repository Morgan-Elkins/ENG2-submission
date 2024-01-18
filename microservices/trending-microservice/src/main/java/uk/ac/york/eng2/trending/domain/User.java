package uk.ac.york.eng2.trending.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micronaut.serde.annotation.Serdeable;

@Entity
@Serdeable
public class User {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false, unique= true)
	private String username;
	
	@JsonIgnore
	@ManyToMany(mappedBy="viewers")
	private Set<Video> watchedVideos;
	
	@JsonIgnore
	@ManyToMany(mappedBy="likes")
	private Set<Video> likedVideos;
	
	@JsonIgnore
	@ManyToMany(mappedBy="dislikes")
	private Set<Video> dislikedVideos;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Video> getWatchedVideos() {
		return watchedVideos;
	}

	public void setWatchedVideos(Set<Video> watchedVideos) {
		this.watchedVideos = watchedVideos;
	}

	public Set<Video> getLikedVideos() {
		return likedVideos;
	}

	public void setLikedVideos(Set<Video> likedVideos) {
		this.likedVideos = likedVideos;
	}

	public Set<Video> getDislikedVideos() {
		return dislikedVideos;
	}

	public void setDislikedVideos(Set<Video> dislikedVideos) {
		this.dislikedVideos = dislikedVideos;
	}
	
}
