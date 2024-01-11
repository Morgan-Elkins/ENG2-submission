package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.Video;

@KafkaListener
public class VideosConsumer {
		
	@Topic(VideosProducer.VIDEO_VIEWED_TOPIC)
	void watchedVideo(@KafkaKey Long id, Video v) 
	{
		System.out.println("Video viewed: " + id);
	}
}
