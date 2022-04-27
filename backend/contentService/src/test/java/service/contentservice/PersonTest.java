package service.contentservice;


import org.bson.json.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import service.contentservice.persistence.documentbased.Category;

public class PersonTest {

    @Before
    public void setUp(){

    }

    @Test
    public void testPersistence() throws JSONException {
       // String uri = "";
     //   RestTemplate restTemplate = new RestTemplate();

      //  String data = restTemplate.getForObject(uri, String.class);
        Category category = new Category("123");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("name", "abc");
        System.out.println(jsonObject);

    }

}
