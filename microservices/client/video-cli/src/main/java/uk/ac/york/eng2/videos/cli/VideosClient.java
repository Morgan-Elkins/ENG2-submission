package uk.ac.york.eng2.videos.cli;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;

@Client("${videos.url:`http://localhost:8080/videos`}")
public interface VideosClient {

	@Get("/")
	public Iterable<Video> list();
	
	@Post("/")
	public HttpResponse<Void> add(@Body VideoDTO videoDetails);
	
}
