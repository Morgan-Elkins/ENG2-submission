package uk.ac.york.eng2.videos.cli;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.videos.dto.UserDTO;
import uk.ac.york.eng2.videos.dto.VideoDTO;

@Command(name = "add-user", description = "Adds a user to the database", mixinStandardHelpOptions = true)
public class AddUserCommand implements Runnable {

	@Inject
	UsersClient client;
	
	@Parameters(index="0")
	private String username;


	@Override
	public void run() {
		UserDTO dto = new UserDTO();
		dto.setUsername(username);
		
		HttpResponse<Void> response = client.addUser(dto);
		System.out.println("Server responded with: " + response.getStatus());
		
	}

}
