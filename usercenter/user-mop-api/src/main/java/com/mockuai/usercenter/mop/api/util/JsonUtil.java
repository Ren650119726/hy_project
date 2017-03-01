package com.mockuai.usercenter.mop.api.util;

import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author cwr
 */
public class JsonUtil {

	public static Gson gson = new GsonBuilder().setFieldNamingPolicy(
			FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

	public static <T> T parseJson(String jsonStr, Class<T> tClass) {
		return gson.fromJson(jsonStr, tClass);
	}

	/**
	 * 使用Gson生成json字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String toJson(Object src) {
		return gson.toJson(src);
	}

	public static void main(String args[]){
		String src ="[\"81_87\",\"81_89\"]";
		List<String> list = JsonUtil.parseJson(src,List.class);
		System.out.println(list);
		
	}
	
}
