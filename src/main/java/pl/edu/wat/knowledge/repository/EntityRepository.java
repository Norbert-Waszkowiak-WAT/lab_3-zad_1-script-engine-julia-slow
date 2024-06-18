package pl.edu.wat.knowledge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.edu.wat.knowledge.entity.Entity;

@RepositoryRestResource(collectionResourceRel = "entities", path = "entities")
public interface EntityRepository extends MongoRepository<Entity, String> {
}
