import org.junit.jupiter.api.BeforeEach;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import uk.ac.york.eng2.videos.VideosClient;
import uk.ac.york.eng2.videos.repositories.UsersRepository;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

@MicronautTest
public class VideosControllerClass {
	
	@Inject
	VideosClient client;
	@Inject
	VideosRepository repo;
    @Inject
	UsersRepository userRepo;

    @BeforeEach
    void clean() 
    {
    	repo.deleteAll();
    	userRepo.deleteAll();
    }
}
