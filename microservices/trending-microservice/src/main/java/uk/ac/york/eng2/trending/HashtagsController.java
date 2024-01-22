package uk.ac.york.eng2.trending;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import uk.ac.york.eng2.trending.domain.Hashtags;
import uk.ac.york.eng2.trending.domain.Video;
import uk.ac.york.eng2.trending.dto.HashtagsDTO;
import uk.ac.york.eng2.trending.events.PostedHashtagsProducer;
import uk.ac.york.eng2.trending.events.VideosConsumer;
import uk.ac.york.eng2.trending.repositories.HashtagsRepository;

@Controller("/hashtags")
public class HashtagsController {

	@Inject
    HashtagsRepository repo;
	
	@Inject
	PostedHashtagsProducer postedHashtagsProducer;
	
	@Inject
	VideosConsumer videosConsumer;
	
	@Get("/")
	public Iterable<Hashtags> list() {
		return repo.findAll();
	}
	
	@Post("/")
	public HttpResponse<Void> add(@Body HashtagsDTO hashtagDetails) {
		Hashtags hashtag = new Hashtags();
		hashtag.setUser(hashtagDetails.getUser());
		hashtag.setHashtags(hashtagDetails.getHashtags());

		repo.save(hashtag);

		return HttpResponse.created(URI.create("/hashtags/" + hashtag.getId()));
	}
	
	@Get("/{id}")
    public Hashtags getHashtag(long id) {
        return repo.findById(id).orElse(null);
    }
	
	@Get("/top10")
    public String topTenHashtags() {
		
		List<Video> videos =  videosConsumer.getCurrentListOfVideos();
		String allHashTags = "";
		
		// Get all the hashtags
		for (Video v: videos) 
		{
			allHashTags += "," + v.getHashtags();
		}
		
		// To list
		String[] hashtagsToList = allHashTags.split(",");
		
		
		// Sort by order desc
		final Map<String, Integer> counter = new HashMap<String, Integer>();
		for (String str : hashtagsToList)
		    counter.put(str, 1 + (counter.containsKey(str) ? counter.get(str) : 0));

		List<String> listofHashtags = new ArrayList<String>(counter.keySet());
		Collections.sort(listofHashtags, new Comparator<String>() {
		    @Override
		    public int compare(String x, String y) {
		        return counter.get(y) - counter.get(x);
		    }
		});
		
		
		int i = 0;
		String topTenHashtags = "";
		for (String s : listofHashtags) 
		{
			if (i < 10) 
			{
				topTenHashtags+= s + ",";
			}
			i++;
		}
		
        return topTenHashtags;
    }

}
