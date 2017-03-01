package com.mockuai.deliverycenter.mop.api.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mockuai.deliverycenter.mop.api.dto.MopDeliveryDetailDTO;

public class JsonUtil {
	public static Gson gson = new GsonBuilder().setFieldNamingPolicy(
			FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

	public static <T> T parseJson(String jsonStr, Class<T> tClass) {
		return gson.fromJson(jsonStr, tClass);
	}

	/**
	 * 使用Gson生成json字符串
	 * @param src
	 * @return
	 */
	public static String toJson(Object src) {
		return gson.toJson(src);
	}
	
	public static <T> T parseListJson(String listStr,Type type){
		T objList = gson.fromJson(listStr, type);
		return objList;
	}

	
	public static void main(String args[]){
		String s = "[{content:\"one\",opTime:\"one\"}]";
		Type type =  new TypeToken<ArrayList<MopDeliveryDetailDTO>>() {}.getType();  
		List<MopDeliveryDetailDTO> list = parseListJson(s,type);
		System.out.println(list);
	}
	
}
