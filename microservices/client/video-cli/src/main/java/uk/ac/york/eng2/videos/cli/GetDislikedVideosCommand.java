package uk.ac.york.eng2.videos.cli;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.videos.domain.User;

@Command(name = "get-dislikes-of-video", description = "fetches the dislikes of a specific video", mixinStandardHelpOptions = true)
public class GetDislikedVideosCommand implements Runnable {

	@Inject
	private VideosClient client;
	
	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		Iterable<User> dislikes = client.getDislikes(id);
		for (User u : dislikes) 
		{
			System.out.println(u);
		}
	}
}
