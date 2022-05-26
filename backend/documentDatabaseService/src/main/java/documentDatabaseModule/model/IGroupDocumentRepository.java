package documentDatabaseModule.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupDocumentRepository extends MongoRepository<GroupDocument, ObjectId> {

}
