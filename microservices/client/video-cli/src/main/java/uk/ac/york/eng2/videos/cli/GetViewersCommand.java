package uk.ac.york.eng2.videos.cli;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.videos.domain.User;

@Command(name = "get-viewers", description = "fetches the viewers of a specific video", mixinStandardHelpOptions = true)
public class GetViewersCommand implements Runnable {

	@Inject
	VideosClient client;
	
	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		Iterable<User> viewers = client.getViewers(id);
		for (User u : viewers) 
		{
			System.out.println(u);
		}
	}
}
