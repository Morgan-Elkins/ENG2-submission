package uk.ac.york.eng2.videos.cli;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.videos.domain.Video;

@Command(name = "get-video", description = "fetches a specific video", mixinStandardHelpOptions = true)
public class GetVideoCommand implements Runnable {

	@Inject
	private VideosClient client;
	
	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		Video video = client.getVideo(id);
		System.out.println(video);
	}
}
