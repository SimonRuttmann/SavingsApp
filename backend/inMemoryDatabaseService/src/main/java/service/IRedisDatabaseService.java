package service;

import model.AtomicIntegerModel;
import org.springframework.stereotype.Service;

@Service
public interface IRedisDatabaseService {
    String getSingleSetValue(String key);
    void incrementValue(AtomicIntegerModel key);
}
