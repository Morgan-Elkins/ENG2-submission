package uk.ac.york.eng2.videos.events;

import java.time.Duration;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;

import io.micronaut.configuration.kafka.serde.SerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import uk.ac.york.eng2.videos.domain.Video;
// This whole class is just so I can compile this microservice as is, probably should remove
@Factory
public class VideosStreams {
	
	public static final String TOPIC_VIEWED_BY_DAY = "video_viewed_by_day";
	
	@Inject
	private SerdeRegistry serdeRegistry;
	
	@Singleton
	public KStream<WindowedIdentifier, Long> videosWatchedByDay(ConfiguredStreamBuilder builder)
	{
		Properties props = builder.getConfiguration();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "videos-streams");

		props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

		KStream<Long, Video> videosStream = builder
			.stream(VideosProducer.VIDEO_VIEWED_TOPIC, Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)));

		KStream<WindowedIdentifier, Long> stream = videosStream.groupByKey()
			.windowedBy(TimeWindows.of(Duration.ofDays(1)).advanceBy(Duration.ofDays(1)))
			.count(Materialized.as("viewed-by-day"))
			.toStream()
			.selectKey((k, v) -> new WindowedIdentifier(k.key(), k.window().start(), k.window().end()));

		stream.to(TOPIC_VIEWED_BY_DAY,
			Produced.with(serdeRegistry.getSerde(WindowedIdentifier.class), Serdes.Long()));

		return stream;
	}	
}
