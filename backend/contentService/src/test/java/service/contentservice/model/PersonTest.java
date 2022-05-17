package service.contentservice.model;


import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import documentDatabaseService.documentbased.model.Category;
import service.contentservice.persistence.relational.entity.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Map;

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


        ObjectId objectId = new ObjectId();
        System.out.println(objectId.toHexString());

        ObjectId newObjectId = new ObjectId(objectId.toHexString());
        System.out.println(newObjectId.toHexString());

        Assertions.assertEquals(objectId, newObjectId);

        Person p = fromXml(Person.class, "");

        p = get("abc");

        //Assertions a = fromXml2("");
     //   Person p2 = PersonTest.<String>get("abc");
    }
    public static <T> T fromXml(Class<T> clazz, String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller um = context.createUnmarshaller();
            return clazz.cast(um.unmarshal(new StringReader(xml)));
        } catch (JAXBException je) {
            throw new RuntimeException("Error interpreting XML response", je);
        }
    }

    public static <T> T fromXml2(String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance();
            Unmarshaller um = context.createUnmarshaller();
            return (T)um.unmarshal(new StringReader(xml)); //Watn bullshit, mal ganz ehrlich...        //Assertions a = fromXml2("");
        } catch (JAXBException je) {
            throw new RuntimeException("Error interpreting XML response", je);
        }
    }


    private static Map<String, Object> data;
    public static <T> T get(String key) {
        return (T) data.get(key);
    }


}
