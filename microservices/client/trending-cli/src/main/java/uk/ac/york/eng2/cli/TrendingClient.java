package uk.ac.york.eng2.cli;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.trending.domain.Hashtags;
import uk.ac.york.eng2.trending.dto.HashtagsDTO;

@Client("${hashtags.url:`http://localhost:8081/hashtags`}")
public interface TrendingClient {
	
	@Get("/")
	public Iterable<Hashtags> list();
	
	@Post("/")
	public HttpResponse<Void> add(@Body HashtagsDTO hashtagDetails);
	
	@Get("/{id}")
    public Hashtags getHashtag(long id);
	
	@Get("/top10")
    public String topTenHashtags();
}
