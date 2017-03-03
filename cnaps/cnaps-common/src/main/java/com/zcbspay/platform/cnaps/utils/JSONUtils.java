package com.zcbspay.platform.cnaps.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class JSONUtils {
	
	 // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

	public static JSONArray xml2json(String xmlPath) throws Exception {
		String xmlStr = IOUtils.toString(new FileInputStream(xmlPath), "UTF-8");
		JSON json = new XMLSerializer().read(xmlStr);
		return JSONArray.fromObject(json.toString());
	}

	public static String elemText(JSONObject obj, String key) {
		String ret = null;
		JSONObject tmp = obj.getJSONObject(key);
		if (tmp.get("#text") != null) {
			ret = tmp.getString("#text");
		}
		return ret;
	}

	public static void copy(JSONObject src, JSONObject dest) {
		for (Object key : src.keySet()) {
			dest.put(key, src.get(key));
		}
	}

	public static void copyElem(JSONObject src, JSONObject dest) {
		for (Object key : src.keySet()) {
			if (!((String) key).startsWith("@")) {
				dest.put(key, src.get(key));
			}
		}
	}

	public static void copyPart(JSONObject src, JSONObject dest) {
		for (Object key : src.keySet()) {
			if (!dest.containsKey(key)) {
				dest.put(key, src.get(key));
			}
		}
	}

	public static JSONObject sort(JSONObject obj) {
		return JSONObject.fromObject(JSONObject.toBean(obj, TreeMap.class));
	}
	 /** 
     * 对象转换成JSON字符串 
     * @param obj   需要转换的对象 
     * @return 对象的string字符 
     */  
    public static String toJson(Object obj) {  
        JSONObject jSONObject = JSONObject.fromObject(obj);  
        return jSONObject.toString();  
    }  
  
    /** 
     * JSON字符串转换成对象 
     * @param jsonString 需要转换的字符串 
     * @param type  需要转换的对象类型 
     * @return 对象 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T fromJson(String jsonString, Class<T> type) {  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        return (T) JSONObject.toBean(jsonObject, type);  
    }  
  
    /** 
     * 将JSONArray对象转换成list集合 
     * @param jsonArr 
     * @return 
     */  
    public static List<Object> jsonToList(JSONArray jsonArr) {  
        List<Object> list = new ArrayList<Object>();  
        for (Object obj : jsonArr) {  
            if (obj instanceof JSONArray) {  
                list.add(jsonToList((JSONArray) obj));  
            } else if (obj instanceof JSONObject) {  
                list.add(jsonToMap((JSONObject) obj));  
            } else {  
                list.add(obj);  
            }  
        }  
        return list;  
    }
    
    
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
  
    /** 
     * 将json字符串转换成map对象 
     *  
     * @param json 
     * @return 
     */  
    public static Map<String, Object> jsonToMap(String json) {  
        JSONObject obj = JSONObject.fromObject(json);  
        return jsonToMap(obj);  
    }  
  
    /** 
     * 将JSONObject转换成map对象 
     * @param json 
     * @return 
     */  
    public static Map<String, Object> jsonToMap(JSONObject obj) {  
        Set<?> set = obj.keySet();  
        Map<String, Object> map = new HashMap<String, Object>(set.size());  
        for (Object key : obj.keySet()) {  
            Object value = obj.get(key);  
            if (value instanceof JSONArray) {  
                map.put(key.toString(), jsonToList((JSONArray) value));  
            } else if (value instanceof JSONObject) {  
                map.put(key.toString(), jsonToMap((JSONObject) value));  
            } else {  
                map.put(key.toString(), obj.get(key));  
            }  
  
        }  
        return map;  
    }  
}
