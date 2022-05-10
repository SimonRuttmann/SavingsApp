package service.advertisementservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import javax.annotation.Resource;

@SpringBootTest
class AdvertisementServiceApplicationTests {
    @Autowired
    private RedisTemplate<String,String> template;

    @Resource(name="redisTemplate")
    private SetOperations<String,String> setOperations;

    @Test
    void fillDatabase() {
        setOperations.add("sendMessages","55");
        setOperations.add("registItems","12");
        setOperations.add("countUser","3");

        assert (setOperations.isMember("sendMessages", "55"));
        assert (setOperations.isMember("registItems","12"));
        assert (setOperations.isMember("countUser","3"));

    }

}
