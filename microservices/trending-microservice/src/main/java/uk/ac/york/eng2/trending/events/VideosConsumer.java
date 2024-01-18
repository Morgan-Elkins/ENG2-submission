package uk.ac.york.eng2.trending.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaListener(groupId = "videos-debug")
public class VideosConsumer {
		
	@Topic(VideosStreams.TOPIC_POSTED_BY_HOUR)
	public void videoWatchedMetric(@KafkaKey WindowedIdentifier window, Long count) {
		System.out.printf("New value for key %s: %d%n", window, count);
	}

}
