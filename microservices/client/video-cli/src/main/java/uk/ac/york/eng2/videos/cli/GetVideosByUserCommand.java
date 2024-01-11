package uk.ac.york.eng2.videos.cli;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.videos.domain.Video;

@Command(name = "get-videos-by-user", description = "fetches all the videos from a specific user", mixinStandardHelpOptions = true)
public class GetVideosByUserCommand implements Runnable {

	@Inject
	private VideosClient client;

	@Parameters(index="0")
	private String userName;
	
	@Override
	public void run() {
		for (Video v : client.list()) {
			if (v.getUser().equals(userName)) 
			{
				System.out.println(v);				
			}
		}
		// TODO Auto-generated method stub

	}
}
