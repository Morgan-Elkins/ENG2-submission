package uk.ac.york.eng2.videos.repositories;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.videos.domain.Video;

@Repository
public interface VideosRepository extends CrudRepository<Video, Long>{

	@Join(value = "likes", type = Join.Type.LEFT_FETCH)
	@Join(value = "dislikes", type = Join.Type.LEFT_FETCH)
	@Join(value = "viewers", type = Join.Type.LEFT_FETCH)
	@Override
	Optional<Video> findById(@NotNull Long id);
	
}
