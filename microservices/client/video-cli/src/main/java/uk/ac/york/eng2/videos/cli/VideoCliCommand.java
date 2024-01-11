package uk.ac.york.eng2.videos.cli;

import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "video-cli", description = "...", 
subcommands = {
		GetVideosCommand.class, 
		AddVideoCommand.class, 
		GetVideoCommand.class,
		UpdateVideoCommand.class,
		DeleteVideoCommand.class,
		AddUserCommand.class,
		GetUsersCommand.class,
		GetViewersCommand.class,
		AddViewerCommand.class,
		DeleteViewerCommand.class,
		GetDislikedVideosCommand.class,
		GetLikedVideosCommand.class,
		AddLikeCommand.class,
		AddDislikeCommand.class,
		GetVideosByUserCommand.class,
		GetVideosByHashtagCommand.class}, 
mixinStandardHelpOptions = true) 
public class VideoCliCommand implements Runnable {

	@Option(names = { "-v", "--verbose" }, description = "...")
	boolean verbose;

	public static void main(String[] args) throws Exception {
		PicocliRunner.run(VideoCliCommand.class, args);
	}

	public void run() {
		// business logic here
		if (verbose) {
			System.out.println("Hi!");
		}
	}
}
