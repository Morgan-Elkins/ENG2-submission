package uk.ac.york.eng2.videos.controllers;

import java.net.URI;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.dto.UserDTO;
import uk.ac.york.eng2.videos.repositories.UsersRepository;

@Controller("/users")
public class UsersController {
	@Inject
	UsersRepository repo;
	
	@Get("/")
	Iterable<User> list()
	{
		return repo.findAll();
	}
	
	@Post("/")
	HttpResponse<Void> addUser(@Body UserDTO userDetails)
	{
		User u = new User();
		u.setUsername(userDetails.getUsername());
		repo.save(u);
		return HttpResponse.created(URI.create("/users/" + u.getId()));
	} 
}
