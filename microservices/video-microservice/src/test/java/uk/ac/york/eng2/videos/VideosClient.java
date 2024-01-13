package uk.ac.york.eng2.videos;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;

@Client("/videos")
public interface VideosClient {

	@Get("/")
	public Iterable<Video> list();
	
	@Post("/")
	public HttpResponse<Void> add(@Body VideoDTO videoDetails);
	
	@Get("/{id}")
	public Video getVideo(long id);
	
	@Put("/{id}")
	public HttpResponse<Void> updateVideo(long id, @Body VideoDTO videoDetails);

	@Delete("/{id}")
	public HttpResponse<Void> deleteVideo(long id);

	@Get("/{id}/viewers")
	public Iterable<User> getViewers(long id);

	@Put("/{videoId}/viewers/{userId}")
	public HttpResponse<Void> addViewer(long videoId, long userId);
	
	@Delete("/{videoId}/viewers/{userId}")
	public HttpResponse<Void> deleteViewer(long videoId, long userId);
	
	@Get("/{id}/likes")
	public Iterable<User> getLikes(long id);
	
	@Get("/{id}/dislikes")
	public Iterable<User> getDislikes(long id);
	
	@Put("/{videoId}/likes/{userId}")
	public HttpResponse<Void> addLike(long videoId, long userId);
	
	@Put("/{videoId}/dislikes/{userId}")
	public HttpResponse<Void> addDislike(long videoId, long userId);
}
