package inMemoryDatabaseService.service;

import inMemoryDatabaseService.model.AtomicIntegerModel;
import org.springframework.stereotype.Service;

@Service
public interface IRedisDatabaseService {

    void incrementValue(AtomicIntegerModel key);

}
