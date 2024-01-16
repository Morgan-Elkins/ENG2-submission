package uk.ac.york.eng2.videos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.events.VideosProducer;
import uk.ac.york.eng2.videos.repositories.UsersRepository;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

@Property(name = "spec.name", value = "KafkaProductionTest")
@MicronautTest(transactional = false, environments = "no_streams")
public class KafkaProductionTest {
	@Inject
	VideosClient client;
	@Inject
	VideosRepository repo;
    @Inject
	UsersRepository userRepo;
    
    private static final Map<Long, Video> viewedVideos = new HashMap<>();

	@BeforeEach
	public void setUp() {
		repo.deleteAll();
		userRepo.deleteAll();
		viewedVideos.clear();
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

		// Check the event went to Kafka and back
		Awaitility.await()
			.atMost(Duration.ofSeconds(30))
			.until(() -> viewedVideos.containsKey(videoId));
	}
    
    @Requires(property = "spec.name", value = "KafkaProductionTest")
	@KafkaListener(groupId = "kafka-production-test")
	static class TestConsumer {
		@Topic(VideosProducer.VIDEO_VIEWED_TOPIC)
		void viewedVideo(@KafkaKey Long id, Video video) {
			viewedVideos.put(id, video);
		}
	}
}
