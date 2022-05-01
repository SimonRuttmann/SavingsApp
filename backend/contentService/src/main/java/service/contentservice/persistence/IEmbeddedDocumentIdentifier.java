package service.contentservice.persistence;

import org.bson.types.ObjectId;

public interface IEmbeddedDocumentIdentifier {
    void setIdIfNotExists();
    ObjectId getId();

    void setId(ObjectId id);

}
