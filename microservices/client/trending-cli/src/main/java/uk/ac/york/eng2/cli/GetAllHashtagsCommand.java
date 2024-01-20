package uk.ac.york.eng2.cli;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import uk.ac.york.eng2.trending.domain.Hashtags;

@Command(name = "get-all-hashtags", description= "fetches all the hashtags", 
	mixinStandardHelpOptions = true)
public class GetAllHashtagsCommand implements Runnable {

	@Inject
	private TrendingClient client;
	
	@Override
	public void run() {
		for (Hashtags h : client.list()) 
		{
			System.out.println(h);
		}
	}
}
