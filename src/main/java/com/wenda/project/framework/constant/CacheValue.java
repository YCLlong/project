package com.wenda.project.framework.constant;

import java.util.HashMap;
import java.util.Map;

public class CacheValue {
    public static final String STATUS = "status";
    public static final String USER_TYPE = "user_type";
    private static Map<String,IConvertable[]> convertPool = new HashMap<>();

    static {
        intiConvertAble();
    }


    public static void intiConvertAble(){
        convertPool.put(STATUS,StatusEnum.values());
        convertPool.put(USER_TYPE,UserTypeEnum.values());
    }

    public static IConvertable[] getConvert(String key){
        if(key == null || "".equals(key)){
            return null;
        }
        return convertPool.get(key);
    }

    public static String getConvert(String key,int id){
        IConvertable[] convertable = getConvert(key);
        if(convertable == null){
            return "";
        }
        for(IConvertable temp:convertable){
            if(id == temp.getId()){
                return temp.getName();
            }
        }
        return "";
    }
}
