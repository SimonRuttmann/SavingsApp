package service;

import model.AtomicIntegerModel;
import org.springframework.stereotype.Service;

@Service
public interface IRedisDatabaseService {
    Integer getSingleSetValue(AtomicIntegerModel key);
    void incrementValue(AtomicIntegerModel key);
}
