package uk.ac.york.eng2.cli;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.trending.dto.HashtagsDTO;

@Command(name = "add-hashtag", description= "Adds a hashtag to the database", 
	mixinStandardHelpOptions = true)
public class AddHashtagsCommand implements Runnable {

	@Inject
	private TrendingClient client;
	
	@Parameters(index="0")
	private String user;
	
	@Parameters(index="1")
	private String hashtags;
	
	@Override
	public void run() {
		HashtagsDTO dto = new HashtagsDTO();
		dto.setUser(user);
		dto.setHashtags(hashtags);
		
		client.add(dto);
		
		HttpResponse<Void> response = client.add(dto);
		System.out.println("Server resp with:" + response.getStatus());
	}
}
