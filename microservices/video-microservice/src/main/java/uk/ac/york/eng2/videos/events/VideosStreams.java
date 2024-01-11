package uk.ac.york.eng2.videos.events;

import java.time.Duration;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindowedKStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;

import io.micronaut.configuration.kafka.serde.SerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import uk.ac.york.eng2.videos.domain.Video;


// This whole class is just so I can compile this microservice as is, probably should remove
@Factory
public class VideosStreams {
	
	public String TOPIC_READ_BY_DAY = "video_read_by_day";
	
	@Inject
	private SerdeRegistry serdeRegistry;
	
	@Singleton
	public KStream<WindowedIdentifier, Long> videosWatchedByDay(ConfiguredStreamBuilder builder)
	{
		Properties props = builder.getConfiguration();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "videos-streams");
		props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);
		
		// (1L, Video {...})
		KStream<Long, Video> stream = builder.stream(VideosProducer.VIDEO_VIEWED_TOPIC, Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)));
	
		// (1L, [Video {...},[Video {...}, ...])
		KGroupedStream<Long, Video> groupedStream = stream.groupByKey();
		
		// (1L, [Video {...},[Video {...}, ...]) by day
		TimeWindowedKStream<Long, Video> groupedByDayStream = groupedStream.windowedBy(TimeWindows.of(Duration.ofDays(1)).advanceBy(Duration.ofDays(1)));
		
		// Table from video ID + window to latest count
		KTable<Windowed<Long>, Long> countsByKey = groupedByDayStream.count();
		
		// Stream of updates from the table
		KStream<Windowed<Long>, Long> countsStream = countsByKey.toStream();
		
		// Put into JSON
		KStream<WindowedIdentifier, Long> jsonCountStream = countsStream.selectKey(
				(key,value) -> new WindowedIdentifier(key.key(), key.window().start(), key.window().end())
		);
		
		jsonCountStream.to(TOPIC_READ_BY_DAY, Produced.with(serdeRegistry.getSerde(WindowedIdentifier.class), Serdes.Long()));
		
		return jsonCountStream;
	}
}
