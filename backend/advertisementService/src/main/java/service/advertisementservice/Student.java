package service.advertisementservice;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Student")
public class Student implements Serializable {
    public Student(String id, String name, Gender gender,int grade){
        this.id=id;
        this.name=name;
        this.gender=gender;
        this.grade=grade;
    }
    public enum Gender {
        MALE, FEMALE
    }

    private String id;
    private String name;
    private Gender gender;
    private int grade;
}
