package service.contentservice.services;

import service.contentservice.persistence.documentbased.GroupDocument;
import service.contentservice.persistence.documentbased.IGroupDocumentRepository;

public class GroupDocumentService {

    private IGroupDocumentRepository groupDocumentRepository;

    public void saveGroupDocumentService(GroupDocument groupDocument){
        groupDocumentRepository.save(groupDocument);
    }
}
