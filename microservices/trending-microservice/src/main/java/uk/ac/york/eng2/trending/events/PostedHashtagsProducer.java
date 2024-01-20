package uk.ac.york.eng2.trending.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface PostedHashtagsProducer {
	String GET_TOP_10_HASHTAGS = "get-top-10";
	
	@Topic(GET_TOP_10_HASHTAGS)
	void getTopTenHashtags();
}
