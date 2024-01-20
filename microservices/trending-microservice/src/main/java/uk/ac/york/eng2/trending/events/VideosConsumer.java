package uk.ac.york.eng2.trending.events;

import java.util.ArrayList;
import java.util.List;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.trending.domain.Video;

@KafkaListener(groupId = "videos-debug")
public class VideosConsumer {
	
	List<Video> currentListOfVideos = new ArrayList<>();
		
	@Topic(VideosStreams.TOPIC_POSTED_BY_HOUR)
	public void videoPostedByHourMetric(@KafkaKey WindowedIdentifier window, List<Video> videos) {
		if (videos.get(0) != null) 
		{
			System.out.printf("New value for key %s: %d%n", window, videos.get(0));
			currentListOfVideos = videos;
		}
	}

	@Topic(VideosStreams.VIDEO_POSTED_TOPIC)
	public void videoPostedMetric(@KafkaKey Long count, Video video) {
		System.out.printf("New value for key %d%n", count);
	}
	
	public List<Video> getCurrentListOfVideos()
	{
		return currentListOfVideos;
	}
}
