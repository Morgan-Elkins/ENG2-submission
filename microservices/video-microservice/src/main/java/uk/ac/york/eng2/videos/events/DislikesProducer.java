package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.Video;

@KafkaClient
public interface DislikesProducer {
	
	String VIDEO_DISLIKED_TOPIC = "video-disliked";
	
	@Topic(VIDEO_DISLIKED_TOPIC)
	void dislikedVideo(@KafkaKey Long id, Video v);
}
