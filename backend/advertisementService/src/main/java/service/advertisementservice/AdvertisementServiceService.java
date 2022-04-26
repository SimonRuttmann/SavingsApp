package service.advertisementservice;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AdvertisementServiceService {
    public String fetchData(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Diagramm1",getData1());
        hashMap.put("Diagramm2",getData2());
        hashMap.put("Diagramm3",getData3());

        JSONObject jsonObject = new JSONObject(hashMap);

        return jsonObject.toJSONString();
    }

    // TODO: 26.04.2022 Naming
    private String getData1(){
        String value = "721";
        return value;
    }

    @Autowired
    StudentRepository studentRepository;
    private String getData2(){
        Student student = new Student(
                "Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.save(student);

        String value = null;
        return value;
    }
    private String getData3(){
        String value = null;
        return value;
    }
}
