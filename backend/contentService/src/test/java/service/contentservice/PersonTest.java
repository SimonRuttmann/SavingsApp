package service.contentservice;


import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class PersonTest {

    @Before
    public void setUp(){

    }

    @Test
    public void testPersistence(){
        String uri = "";
        RestTemplate restTemplate = new RestTemplate();

        String data = restTemplate.getForObject(uri, String.class);

    }

}
