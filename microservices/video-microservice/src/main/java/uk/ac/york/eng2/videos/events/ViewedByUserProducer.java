package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;

@KafkaClient
public interface ViewedByUserProducer {
	
	String VIDEO_VIEWED_BY_USER = "viewed-by-user";
	
	@Topic(VIDEO_VIEWED_BY_USER)
	void viewedByUser(@KafkaKey Long vId, Long uId, Video v);
}
