package uk.ac.york.eng2.trending;

import java.util.List;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import uk.ac.york.eng2.trending.domain.Hashtags;
import uk.ac.york.eng2.trending.events.PostedHashtagsProducer;
import uk.ac.york.eng2.trending.repositories.HashtagsRepository;

@Controller("/hashtags")
public class HashtagsController {

	@Inject
    HashtagsRepository repo;
	
	@Inject
	PostedHashtagsProducer postedHashtagsProducer;
	
	@Get("/{id}")
    public Hashtags getHashtag(long id) {
        return repo.findById(id).orElse(null);
    }
	
	@Get("/top10")
    public List<Hashtags> topTenHashtags() {
		postedHashtagsProducer.getTopTenHashtags();
        return repo.findTop10OrderByHashtagsDesc();
    }

}
