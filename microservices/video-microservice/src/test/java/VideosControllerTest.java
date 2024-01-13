import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import uk.ac.york.eng2.videos.VideosClient;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.repositories.UsersRepository;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

@MicronautTest
public class VideosControllerTest {
	
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
    
    @Test
    public void noBooks() {
    	Iterable<Video> iterBooks = client.list();
    	assertFalse(iterBooks.iterator().hasNext(),
    			"Service should not list any books initially");
    }
}
