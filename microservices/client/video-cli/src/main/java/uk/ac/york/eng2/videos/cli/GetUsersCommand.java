package uk.ac.york.eng2.videos.cli;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import uk.ac.york.eng2.videos.domain.User;

@Command(name = "get-users", description = "fetches all the users", mixinStandardHelpOptions = true)
public class GetUsersCommand implements Runnable {

	@Inject
	private UsersClient client;

	@Override
	public void run() {
		for (User v : client.list()) {
			System.out.println(v);
		}
	}
}
