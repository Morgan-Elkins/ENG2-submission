package uk.ac.york.eng2.trending.repositories;

import java.util.List;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.trending.domain.Hashtags;

@Repository
public interface HashtagsRepository extends CrudRepository<Hashtags, Long> {

	List<Hashtags> findTop10OrderByHashtagsDesc();
}
