package uk.ac.york.eng2.videos.cli;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.videos.dto.VideoDTO;

@Command(name = "update-video", description = "updates a specific video", mixinStandardHelpOptions = true)
public class UpdateVideoCommand implements Runnable {

	@Inject
	private VideosClient client;
	
	@Parameters(index="0")
	private Long id;
	
	@Option(names={"-t", "--title"}, description="Title of the video")
	private String title;
	
	@Option(names={"-u", "--user"}, description="User who posted the video")
	private String user;
	
	@Option(names={"-ht", "--hashtags"}, description="Hashtags of the video")
	private String hashtags;

	@Override
	public void run() {
		VideoDTO dto = new VideoDTO();
		dto.setTitle(title);
		dto.setUser(user);
		dto.setHashtags(hashtags);
		
		HttpResponse<Void> response =  client.updateVideo(id, dto);
		
		System.out.println("Server responded with: " + response.getStatus());
	}
}
