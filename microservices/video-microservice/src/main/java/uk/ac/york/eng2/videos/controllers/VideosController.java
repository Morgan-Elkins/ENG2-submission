package uk.ac.york.eng2.videos.controllers;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;
import uk.ac.york.eng2.videos.events.DislikesProducer;
import uk.ac.york.eng2.videos.events.LikesProducer;
import uk.ac.york.eng2.videos.events.PostedVideoProducer;
import uk.ac.york.eng2.videos.events.VideosProducer;
import uk.ac.york.eng2.videos.events.ViewedByUserProducer;
import uk.ac.york.eng2.videos.repositories.UsersRepository;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

@Controller("/videos")
public class VideosController {

	@Inject
	VideosRepository repo;
	
	@Inject
	UsersRepository userRepo;
	
	@Inject
	VideosProducer videoProducer;
	
	@Inject
	LikesProducer likesProducer;
	
	@Inject
	DislikesProducer dislikesProducer;
	
	@Inject
	ViewedByUserProducer viewedByUserProducer;
	
	@Inject
	PostedVideoProducer postedVideoProducer;
	

	@Get("/")
	public Iterable<Video> list() {
		return repo.findAll();
	}
	
	@Post("/")
	public HttpResponse<Void> add(@Body VideoDTO videoDetails)
	{
		Video video = new Video();
		video.setTitle(videoDetails.getTitle());
		video.setUser(videoDetails.getUser());
		video.setHashtags(videoDetails.getHashtags());

		repo.save(video);
		
		postedVideoProducer.videoPosted(video.getId(), video);
		
		return HttpResponse.created(URI.create("/videos/" + video.getId()));
	}
	
	@Get("/{id}")
	public Video getVideo(long id) 
	{
		return repo.findById(id).orElse(null);
	}
	
	@Transactional
	@Put("/{id}")
	public HttpResponse<Void> updateVideo(long id, @Body VideoDTO videoDetails)
	{
		Optional<Video> video = repo.findById(id);
		if (video.isEmpty()) 
		{
			return HttpResponse.notFound();
		}
		
		Video v = video.get();
		if (videoDetails.getTitle() != null) 
		{
			v.setTitle(videoDetails.getTitle());
		}
		if (videoDetails.getUser() != null) 
		{
			v.setUser(videoDetails.getUser());
		}
		if (videoDetails.getHashtags() != null) 
		{
			v.setHashtags(videoDetails.getHashtags());
		}
		repo.update(v);
		
		return HttpResponse.ok();
	}
	
	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteVideo(long id)
	{
		boolean exists = repo.existsById(id);
		if (!exists) 
		{
			return HttpResponse.notFound();
		}
		
		repo.deleteById(id);
		return HttpResponse.ok();
	}
	
	@Get("/{id}/viewers")
	public Iterable<User> getViewers(long id)
	{
		Optional<Video> optVideo =  repo.findById(id);
	
		if (optVideo.isEmpty()) 
		{
			return null;
		}
		return optVideo.get().getViewers();
	}
	
	@Get("/{id}/likes")
	public Iterable<User> getLikes(long id)
	{
		Optional<Video> optVideo =  repo.findById(id);
	
		if (optVideo.isEmpty()) 
		{
			return null;
		}
		return optVideo.get().getLikes();
	}
	
	@Get("/{id}/dislikes")
	public Iterable<User> getDislikes(long id)
	{
		Optional<Video> optVideo =  repo.findById(id);
	
		if (optVideo.isEmpty()) 
		{
			return null;
		}
		return optVideo.get().getDislikes();
	}
	
	@Transactional
	@Put("/{videoId}/viewers/{userId}")
	public HttpResponse<Void> addViewer(long videoId, long userId)
	{
		Optional<Video> oVideo = repo.findById(videoId);
		
		if (oVideo.isEmpty()) 
		{
			return HttpResponse.notFound();
		}
		
		Optional<User> oUser = userRepo.findById(userId);
		
		if (oUser.isEmpty()) 
		{
			return HttpResponse.notFound();
		}
		
		Video video = oVideo.get();
		User user = oUser.get();
		if (video.getViewers().add(user)) 
		{
			repo.update(video);
			videoProducer.watchedVideo(videoId, video);
			viewedByUserProducer.viewedByUser(videoId, userId, video);
		}
		
		return HttpResponse.ok();
	}
	
	@Transactional
	@Put("/{videoId}/likes/{userId}")
	public HttpResponse<Void> addLike(long videoId, long userId)
	{
		Optional<Video> oVideo = repo.findById(videoId);
		
		if (oVideo.isEmpty()) 
		{
			return HttpResponse.notFound();
		}
		
		Optional<User> oUser = userRepo.findById(userId);
		
		if (oUser.isEmpty()) 
		{
			return HttpResponse.notFound();
		}
		
		Video video = oVideo.get();
		User user = oUser.get();
		if (video.getLikes().add(user)) 
		{
			repo.update(video);
			likesProducer.likedVideo(videoId, video);
		}
		
		
		return HttpResponse.ok();
	}
	
	@Transactional
	@Put("/{videoId}/dislikes/{userId}")
	public HttpResponse<Void> addDislike(long videoId, long userId)
	{
		Optional<Video> oVideo = repo.findById(videoId);
		
		if (oVideo.isEmpty()) 
		{
			return HttpResponse.notFound();
		}
		
		Optional<User> oUser = userRepo.findById(userId);
		
		if (oUser.isEmpty()) 
		{
			return HttpResponse.notFound();
		}
		
		Video video = oVideo.get();
		User user = oUser.get();
		if (video.getDislikes().add(user)) {
			repo.update(video);
			dislikesProducer.dislikedVideo(videoId, video);
		}
		return HttpResponse.ok();
	}
	
	@Transactional
	@Delete("/{videoId}/viewers/{userId}")
	public HttpResponse<Void> deleteViewer(long videoId, long userId)
	{
		Optional<Video> oVideo = repo.findById(videoId);
		if (oVideo.isEmpty()) 
		{
			return HttpResponse.notFound();
		}

		Video video = oVideo.get();
		video.getViewers().removeIf(t -> t.getId() == userId);
		repo.update(video);
		
		return HttpResponse.ok(); 
	}
}
