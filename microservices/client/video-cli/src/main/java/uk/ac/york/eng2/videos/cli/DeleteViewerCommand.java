package uk.ac.york.eng2.videos.cli;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "delete-viewer", description = "Deletes a viewer from a video", mixinStandardHelpOptions = true)
public class DeleteViewerCommand implements Runnable {

	@Inject
	private VideosClient client;
	
	@Parameters(index="0")
	private Long videoId;
	
	@Parameters(index="1")
	private Long userId;
	

	@Override
	public void run() {
		HttpResponse<Void> response = client.deleteViewer(videoId, userId);
		System.out.println("Server responded with: " + response.getStatus());
		
	}
}
