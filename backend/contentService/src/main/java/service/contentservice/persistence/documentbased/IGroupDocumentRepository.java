package service.contentservice.persistence.documentbased;

import org.springframework.data.mongodb.repository.MongoRepository;
import service.contentservice.entity2.DocumentEntity;

public interface IGroupDocumentRepository extends MongoRepository<GroupDocument, String> {

}
