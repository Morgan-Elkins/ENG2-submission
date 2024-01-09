package uk.ac.york.eng2.videos.cli;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.dto.UserDTO;

@Client("${users.url:`http://localhost:8080/users`}")
public interface UsersClient {
	
	@Get("/")
	Iterable<User> list();
	
	@Post("/")
	HttpResponse<Void> addUser(@Body UserDTO userDetails);

}
