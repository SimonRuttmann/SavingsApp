package documentDatabaseModule.service;

import org.bson.types.ObjectId;

public interface IEmbeddedDocumentIdentifier {
    void setIdIfNotExists();
    ObjectId getId();

    void setId(ObjectId id);

}
