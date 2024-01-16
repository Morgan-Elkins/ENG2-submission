package uk.ac.york.eng2.videos;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KafkaStreams.State;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.events.VideosProducer;
import uk.ac.york.eng2.videos.events.VideosStreams;
import uk.ac.york.eng2.videos.events.WindowedIdentifier;

@Property(name = "spec.name", value="MicronautStreamsTest")
@TestInstance(Lifecycle.PER_CLASS)
@MicronautTest
public class MicronautStreamsTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MicronautStreamsTest.class);

	@Inject
	VideosProducer producer;

	@Inject
	KafkaStreams kStreams;

	private static final Map<WindowedIdentifier, Long> events = new HashMap<>();

	@BeforeEach
	public void setUp() {
		events.clear();
		Awaitility.await().until(() -> kStreams.state().equals(State.RUNNING));
		LOGGER.info("Kafka Streams is RUNNING");
	}

	@AfterAll
	public void cleanUp() {
		kStreams.close();
	}

	@Test
	public void readEventUpdatesCount() {
		LOGGER.info("About to send event");
		producer.watchedVideo(1L, new Video());
		LOGGER.info("Sent event");

		Awaitility.await()
			.atMost(Duration.ofSeconds(30))
			.until(() -> !events.isEmpty());
	}

	@Requires(property = "spec.name", value = "MicronautStreamsTest")
	@KafkaListener(groupId = "micronaut-streams-test")
	static class StreamsListener {
		@Topic(VideosStreams.TOPIC_VIEWED_BY_DAY)
		public void videoWatchedMetric(@KafkaKey WindowedIdentifier window, Long count) {
			LOGGER.info("Received event: {}", window);
			events.put(window, count);
		}
	}
}
