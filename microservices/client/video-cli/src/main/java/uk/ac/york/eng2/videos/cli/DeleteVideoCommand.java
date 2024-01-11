package uk.ac.york.eng2.videos.cli;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "delete-video", description = "Deletes a specific video", mixinStandardHelpOptions = true)
public class DeleteVideoCommand implements Runnable {

	@Inject
	private VideosClient client;
	
	@Parameters(index="0")
	private Long id;

	@Override
	public void run() {
		HttpResponse<Void>  response = client.deleteVideo(id);
		System.out.println("Server respondede with: " + response.getStatus());
	}
}
