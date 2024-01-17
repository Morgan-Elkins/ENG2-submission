package uk.ac.york.eng2.videos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;
import uk.ac.york.eng2.videos.events.VideosProducer;
import uk.ac.york.eng2.videos.repositories.UsersRepository;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

@MicronautTest(transactional = false, environments = "no_streams")
public class VideosControllerTest {
	
	@Inject
	VideosClient client;
	@Inject
	VideosRepository repo;
    @Inject
	UsersRepository userRepo;
    
    private final Map<Long, Video> viewedVideos = new HashMap<>();
    
    @MockBean(VideosProducer.class)
    VideosProducer testProducer() {
     return (key, value) -> {viewedVideos.put(key, value); };
    }

    @BeforeEach
    void clean() 
    {
    	repo.deleteAll();
    	userRepo.deleteAll();
    	viewedVideos.clear();
    }
    
    @Test
    public void noVideos() {
    	Iterable<Video> iterVideos = client.list();
    	assertFalse(iterVideos.iterator().hasNext(),
    			"Service should not list any videos initially");
    }
    
	@Test
	public void getVideo() {
		Video v = new Video();
		v.setTitle("Video1");
		v.setUser("User1");
		v.setHashtags("Hashtag1");
		repo.save(v);

		Video video = client.getVideo(v.getId());
		assertEquals(v.getTitle(), video.getTitle(), "Title should be fetched correctly");
		assertEquals(v.getUser(), video.getUser(), "User should be fetched correctly");
		assertEquals(v.getHashtags(), video.getHashtags(), "Hashtags should be fetched correctly");
	}
	
	@Test
	public void addVideo() {
		final String videoTitle = "Video1";
		final String videoUser = "User1";
		final String videoHashtag = "Hashtag1";

		VideoDTO video = new VideoDTO();
		video.setTitle(videoTitle);
		video.setUser(videoUser);
		video.setHashtags(videoHashtag);
		HttpResponse<Void> response = client.add(video);
		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");

		List<Video> videos = iterableToList(client.list());
		assertEquals(1, videos.size());
		assertEquals(videoTitle, videos.get(0).getTitle());
		assertEquals(videoUser, videos.get(0).getUser());
		assertEquals(videoHashtag, videos.get(0).getHashtags());
	}

	@Test
	public void updateVideo() {
		Video v = new Video();
		v.setTitle("Video1");
		v.setUser("User1");
		v.setHashtags("Hashtag1");
		repo.save(v);

		final String newTitle = "New Title";
		VideoDTO dtoTitle = new VideoDTO();
		dtoTitle.setTitle(newTitle);
		HttpResponse<Void> response = client.updateVideo(v.getId(), dtoTitle);
		assertEquals(HttpStatus.OK, response.getStatus());

		v = repo.findById(v.getId()).get();
		assertEquals(newTitle, v.getTitle());
	}
	
	@Test
	public void addVideoViewer() {
		Video v = new Video();
		v.setTitle("Video1");
		v.setUser("User1");
		v.setHashtags("Hashtag1");
		repo.save(v);

		final String readerUsername = "Username1";
		User u = new User();
		u.setUsername(readerUsername);
		userRepo.save(u);

		final Long videoId = v.getId();
		HttpResponse<Void> response = client.addViewer(videoId, u.getId());
		assertEquals(HttpStatus.OK, response.getStatus(), "Adding viewer to the video should be successful");

		// Check the producer was called by the addition
		assertTrue(viewedVideos.containsKey(videoId));

		v = repo.findById(videoId).get();
		assertEquals(1, v.getViewers().size(), "Video should now have 1 viewer");
		assertEquals(readerUsername, v.getViewers().iterator().next().getUsername());
	}
	
	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}
