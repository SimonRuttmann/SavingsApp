package service.userservice.util;

import org.bson.types.ObjectId;

public class DocObjectIdUtil {

    public static ObjectId toObjectId(String hexString){
        return new ObjectId(hexString);
    }

    public static String toHexString(ObjectId id){
        return id.toHexString();
    }
}
