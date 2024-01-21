package uk.ac.york.eng2.trending.events;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.SlidingWindows;
import org.apache.kafka.streams.kstream.TimeWindowedKStream;
import org.apache.kafka.streams.kstream.Windowed;

import io.micronaut.configuration.kafka.serde.SerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import uk.ac.york.eng2.trending.domain.Video;

@Factory
public class VideosStreams {
public static final String TOPIC_POSTED_BY_HOUR = "video_posted_by_hour";
public static final String VIDEO_POSTED_TOPIC = "video_posted";
	
	@Inject
	private SerdeRegistry serdeRegistry;
	
	
	// This will get all of the videos posted in the last hour
	@Singleton
	public KStream<WindowedIdentifier, List<Video>> videosWatchedByDay(ConfiguredStreamBuilder builder)
	{
		Properties props = builder.getConfiguration();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "videos-streams");

		props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

		KStream<Long, Video> videosStream = builder
			.stream(VIDEO_POSTED_TOPIC, Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)));

		KGroupedStream<Long, Video> groupedStream = videosStream.groupByKey();
		
		TimeWindowedKStream<Long, Video> groupedByHourStream = groupedStream.windowedBy(SlidingWindows.withTimeDifferenceAndGrace(Duration.ofHours(1),Duration.ofHours(1)));
		
					
		KTable<Windowed<Long>, List<Video>> dataReduced = groupedByHourStream.aggregate(ArrayList::new, 
				(key, value, aggregate) -> {
					aggregate.add(value);
		            return aggregate;
		        }, Materialized.as("NewStore"));
		
		KStream<Windowed<Long>, List<Video>> dataStreamed = dataReduced.toStream();
		
		KStream<WindowedIdentifier, List<Video>> jsonCountStream = dataStreamed.selectKey(
				(key,value) -> new WindowedIdentifier(key.key(), key.window().start(), key.window().end())
		);

		jsonCountStream.to(TOPIC_POSTED_BY_HOUR);

		return jsonCountStream;
	}	
}
