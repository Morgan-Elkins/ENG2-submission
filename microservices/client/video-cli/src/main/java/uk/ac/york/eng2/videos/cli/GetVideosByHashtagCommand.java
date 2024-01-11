package uk.ac.york.eng2.videos.cli;

import java.util.Arrays;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.videos.domain.Video;

@Command(name = "get-videos-by-hashtag", description = "fetches all the videos from a specific hashtag", mixinStandardHelpOptions = true)
public class GetVideosByHashtagCommand implements Runnable {

	@Inject
	private VideosClient client;

	@Parameters(index="0")
	private String hashTag;
	
	@Override
	public void run() {
		for (Video v : client.list()) {
			if (Arrays.asList(v.getHashtags().split(",")).contains(hashTag)) 
			{
				System.out.println(v);				
			}
		}
		// TODO Auto-generated method stub
	}
}
