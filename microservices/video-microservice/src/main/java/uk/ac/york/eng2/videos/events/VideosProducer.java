package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.Video;

@KafkaClient
public interface VideosProducer {
	
	String VIDEO_VIEWED_TOPIC = "book-viewed";
	
	@Topic(VIDEO_VIEWED_TOPIC)
	void watchedVideo(@KafkaKey Long id, Video v);
}
