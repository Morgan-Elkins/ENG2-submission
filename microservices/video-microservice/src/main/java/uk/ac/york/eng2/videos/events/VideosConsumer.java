package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.Video;

@KafkaListener(groupId = "videos-debug")
public class VideosConsumer {
		
	@Topic(VideosStreams.TOPIC_VIEWED_BY_DAY)
	public void videoWatchedMetric(@KafkaKey WindowedIdentifier window, Long count) {
		System.out.printf("New value for key %s: %d%n", window, count);
	}

	@Topic(VideosProducer.VIDEO_VIEWED_TOPIC)
	void watchedVideo(@KafkaKey Long id, Video v) 
	{
		System.out.println("Video viewed: " + id);
	}
}
