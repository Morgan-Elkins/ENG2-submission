package uk.ac.york.eng2.videos.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.videos.domain.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long>{

}
