package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.Video;

@KafkaClient
public interface LikesProducer {
	
	String VIDEO_LIKED_TOPIC = "video-liked";
	
	@Topic(VIDEO_LIKED_TOPIC)
	void likedVideo(@KafkaKey Long id, Video v);
}
