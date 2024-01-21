package uk.ac.york.eng2.videos.cli;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.videos.domain.User;

@Command(name = "get-likes-of-video", description = "fetches the likes of a specific video", mixinStandardHelpOptions = true)
public class GetLikedVideosCommand implements Runnable {

	@Inject
	private VideosClient client;
	
	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		Iterable<User> likes = client.getLikes(id);
		for (User u : likes) 
		{
			System.out.println(u);
		}
	}
}
