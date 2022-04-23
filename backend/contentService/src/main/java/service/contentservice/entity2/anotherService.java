package service.contentservice.entity2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
@Qualifier("anotherService")
public class anotherService implements IService{
    @Override
    public void doSthm() {
        System.out.println("anotherService");
    }
}
