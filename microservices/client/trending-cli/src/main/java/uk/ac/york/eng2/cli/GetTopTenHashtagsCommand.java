package uk.ac.york.eng2.cli;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import uk.ac.york.eng2.trending.domain.Hashtags;

@Command(name = "get-top-ten-hashtags", description= "fetches top ten the hashtags", 
	mixinStandardHelpOptions = true)
public class GetTopTenHashtagsCommand implements Runnable {

	@Inject
	private TrendingClient client;
	
	@Override
	public void run() {

		System.out.println(client.topTenHashtags());
	}
}
