package uk.ac.york.eng2.videos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import uk.ac.york.eng2.videos.events.DislikesProducer;
import uk.ac.york.eng2.videos.events.LikesProducer;
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
    private final Map<Long, Video> likedVideos = new HashMap<>();
    private final Map<Long, Video> dislikedVideos = new HashMap<>();
    
    @MockBean(VideosProducer.class)
    VideosProducer testViewedProducer() {
     return (key, value) -> {viewedVideos.put(key, value); };
    }
    
    @MockBean(LikesProducer.class)
    LikesProducer testLikedProducer() {
     return (key, value) -> {likedVideos.put(key, value); };
    }
    
    @MockBean(DislikesProducer.class)
    DislikesProducer testDislikedProducer() {
     return (key, value) -> {dislikedVideos.put(key, value); };
    }

    @BeforeEach
    void clean() 
    {
    	repo.deleteAll();
    	userRepo.deleteAll();
    	viewedVideos.clear();
    	likedVideos.clear();
    	dislikedVideos.clear();
    }
    
    @Test
    public void noVideos() {
    	Iterable<Video> iterVideos = client.list();
    	assertFalse(iterVideos.iterator().hasNext(),
    			"Service should not list any videos initially");
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
	public void getMissingVideo() {
		Video response = client.getVideo(0);
		assertNull(response, "A missing video should produce a 404");
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
	public void deleteVideo() {
		Video v = new Video();
		v.setTitle("Video1");
		v.setUser("User1");
		v.setHashtags("Hashtag1");
		repo.save(v);

		HttpResponse<Void> response = client.deleteVideo(v.getId());
		assertEquals(HttpStatus.OK, response.getStatus());
		
		assertFalse(repo.existsById(v.getId()));
	}
	
	@Test
	public void noVideoViewers() {
		Video v = new Video();
		v.setTitle("Video1");
		v.setUser("User1");
		v.setHashtags("Hashtag1");
		repo.save(v);

		List<User> viewers = iterableToList(client.getViewers(v.getId()));
		assertEquals(0, viewers.size(), "Videos should not have any viewers initially");
	}
	
	@Test
	public void oneVideoViewers() {
		Video v = new Video();
		v.setTitle("Video1");
		v.setUser("User1");
		v.setHashtags("Hashtag1");
		v.setViewers(new HashSet<>());
		repo.save(v);

		User u = new User();
		u.setUsername("Username1");
		userRepo.save(u);

		v.getViewers().add(u);
		repo.update(v);

		List<User> response = iterableToList(client.getViewers(v.getId()));
		assertEquals(1, response.size(), "The one viewer that was added should be listed");
	}
	
	@Test
	public void oneVideoLike() {
		Video v = new Video();
		v.setTitle("Video1");
		v.setUser("User1");
		v.setHashtags("Hashtag1");
		v.setLikes(new HashSet<>());
		repo.save(v);

		User u = new User();
		u.setUsername("Username1");
		userRepo.save(u);

		v.getLikes().add(u);
		repo.update(v);

		List<User> response = iterableToList(client.getLikes(v.getId()));
		assertEquals(1, response.size(), "The one like that was added should be listed");
	}
	
	@Test
	public void oneVideoDislike() {
		Video v = new Video();
		v.setTitle("Video1");
		v.setUser("User1");
		v.setHashtags("Hashtag1");
		v.setDislikes(new HashSet<>());
		repo.save(v);

		User u = new User();
		u.setUsername("Username1");
		userRepo.save(u);

		v.getDislikes().add(u);
		repo.update(v);

		List<User> response = iterableToList(client.getDislikes(v.getId()));
		assertEquals(1, response.size(), "The one dislike that was added should be listed");
	}
	
	@Test
	public void addVideoViewer() {
		Video v = new Video();
		v.setTitle("Video1");
		v.setUser("User1");
		v.setHashtags("Hashtag1");
		repo.save(v);

		User u = new User();
		u.setUsername("Username1");
		userRepo.save(u);

		final Long videoId = v.getId();
		HttpResponse<Void> response = client.addViewer(videoId, u.getId());
		assertEquals(HttpStatus.OK, response.getStatus(), "Adding viewer to the video should be successful");

		// Check the producer was called by the addition
		assertTrue(viewedVideos.containsKey(videoId));

		v = repo.findById(videoId).get();
		assertEquals(1, v.getViewers().size(), "Video should now have 1 viewer");
		assertEquals("Username1", v.getViewers().iterator().next().getUsername());
	}
	
	@Test
	public void addVideoLike() {
		Video v = new Video();
		v.setTitle("Video1");
		v.setUser("User1");
		v.setHashtags("Hashtag1");
		repo.save(v);

		User u = new User();
		u.setUsername("Username1");
		userRepo.save(u);

		final Long videoId = v.getId();
		HttpResponse<Void> response = client.addLike(videoId, u.getId());
		assertEquals(HttpStatus.OK, response.getStatus(), "Adding like to the video should be successful");

		// Check the producer was called by the addition
		assertTrue(likedVideos.containsKey(videoId));

		v = repo.findById(videoId).get();
		assertEquals(1, v.getLikes().size(), "Video should now have 1 like");
		assertEquals("Username1", v.getLikes().iterator().next().getUsername());
	}
	
	@Test
	public void addVideoDislike() {
		Video v = new Video();
		v.setTitle("Video1");
		v.setUser("User1");
		v.setHashtags("Hashtag1");
		repo.save(v);

		User u = new User();
		u.setUsername("Username1");
		userRepo.save(u);

		final Long videoId = v.getId();
		HttpResponse<Void> response = client.addDislike(videoId, u.getId());
		assertEquals(HttpStatus.OK, response.getStatus(), "Adding dislike to the video should be successful");

		// Check the producer was called by the addition
		assertTrue(dislikedVideos.containsKey(videoId));

		v = repo.findById(videoId).get();
		assertEquals(1, v.getDislikes().size(), "Video should now have 1 dislike");
		assertEquals("Username1", v.getDislikes().iterator().next().getUsername());
	}
	
	@Test
	public void deleteVideoViewer() {
		Video v = new Video();
		v.setTitle("Video1");
		v.setUser("User1");
		v.setHashtags("Hashtag1");
		v.setViewers(new HashSet<>());
		repo.save(v);

		User u = new User();
		u.setUsername("Username1");
		userRepo.save(u);

		v.getViewers().add(u);
		repo.update(v);

		HttpResponse<Void> response = client.deleteViewer(v.getId(), u.getId());
		assertEquals(HttpStatus.OK, response.getStatus(), "Removing viewer to the video should be successful");

		v = repo.findById(v.getId()).get();
		assertTrue(v.getViewers().isEmpty(), "Video should have no viewers anymore");
	}
	
	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}
