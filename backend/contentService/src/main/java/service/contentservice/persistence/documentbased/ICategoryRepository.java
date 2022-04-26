package service.contentservice.persistence.documentbased;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICategoryRepository extends MongoRepository<GroupDocument, String> {
}
