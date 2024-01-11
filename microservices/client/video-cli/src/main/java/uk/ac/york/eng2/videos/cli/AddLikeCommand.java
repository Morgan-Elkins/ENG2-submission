package uk.ac.york.eng2.videos.cli;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "add-like", description = "Adds a like to a video", mixinStandardHelpOptions = true)
public class AddLikeCommand implements Runnable {

	@Inject
	private VideosClient client;
	
	@Parameters(index="0")
	private Long videoId;
	
	@Parameters(index="1")
	private Long userId;
	

	@Override
	public void run() {
		HttpResponse<Void> response = client.addLike(videoId, userId);
		System.out.println("Server responded with: " + response.getStatus());
		
	}
}
