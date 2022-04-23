package service.contentservice.entity2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("someService")
public class someService implements IService{
    @Override
    public void doSthm() {
        System.out.println("soemService");
    }
}
