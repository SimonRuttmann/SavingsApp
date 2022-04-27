package service.contentservice.persistence.documentbased;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupDocumentRepository extends MongoRepository<GroupDocument, ObjectId> {

}
