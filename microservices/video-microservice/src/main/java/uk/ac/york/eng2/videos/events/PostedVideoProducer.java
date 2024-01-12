package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.Video;

@KafkaClient
public interface PostedVideoProducer {
	
	String VIDEO_POSTED_TOPIC = "video-posted";
	
	@Topic(VIDEO_POSTED_TOPIC)
	void videoPosted(@KafkaKey Long id, Video v);
}
