package uk.ac.york.eng2.videos;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import uk.ac.york.eng2.videos.domain.Video;

@MicronautTest
class VideoMicroserviceTest {

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }
    
    @Test
    public void noBooks() {
    	Iterable<Video> iterBooks = client.list();
    	assertFalse(iterBooks.iterator().hasNext(),
    			"Service should not list any books initially");
    }


}
