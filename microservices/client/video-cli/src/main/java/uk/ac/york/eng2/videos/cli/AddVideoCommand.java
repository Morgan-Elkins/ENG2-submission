package uk.ac.york.eng2.videos.cli;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;

@Command(name = "add-video", description = "Adds a video to the database", mixinStandardHelpOptions = true)
public class AddVideoCommand implements Runnable {

	@Inject
	VideosClient client;
	
	@Parameters(index="0")
	private String title;
	
	@Parameters(index="1")
	private String user;
	
	@Parameters(index="2")
	private String hashtags;

	@Override
	public void run() {
		VideoDTO dto = new VideoDTO();
		dto.setTitle(title);
		dto.setUser(user);
		dto.setHashtags(hashtags);
		
		HttpResponse<Void> response = client.add(dto);
		System.out.println("Server responded with: " + response.getStatus());
		
	}

}
