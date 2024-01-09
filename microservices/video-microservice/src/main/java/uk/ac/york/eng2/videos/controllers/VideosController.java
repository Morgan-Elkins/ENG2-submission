package uk.ac.york.eng2.videos.controllers;

import java.net.URI;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

@Controller("/videos")
public class VideosController {

	@Inject
	VideosRepository repo;

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
		
		return HttpResponse.created(URI.create("/videos/" + video.getId()));
	}
}
