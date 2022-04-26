package service.contentservice.persistence.documentbased;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IGroupDocumentRepository extends MongoRepository<GroupDocument, String> {

}
