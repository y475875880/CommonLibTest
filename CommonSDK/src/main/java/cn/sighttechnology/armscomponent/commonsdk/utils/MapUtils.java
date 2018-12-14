package cn.sighttechnology.armscomponent.commonsdk.utils;

import java.util.Map;

public class MapUtils {

    public static String getValue(
            Map<String,String> params,String key
    ){
        return  params.get(key) == null ? "":params.get(key);
    }

    public static void putKeyValue(
            Map<String,String> params,String key,String value
    ){
        if (value != null && key!=null){
            params.put(key,value);
        }
    }

}
