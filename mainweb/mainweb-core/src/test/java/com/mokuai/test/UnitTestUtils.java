package com.mokuai.test;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by duke on 16/5/24.
 */
public class UnitTestUtils {


    public void test1(){
        String src = "[{\n" +
                "\t\"categoryTitle\": \"1\",\n" +
                "\t\"subCategoryTitle\": \"wef\",\n" +
                "\t\"productListList\": [{\n" +
                "\t\t\"item_id\": \"3443\",\n" +
                "\t\t\"seller_id\": \"1841254\"\n" +
                "\t}, {\n" +
                "\t\t\"item_id\": \"3442\",\n" +
                "\t\t\"seller_id\": \"1841254\"\n" +
                "\t}]\n" +
                "}]";

        List<?> objList = null;
        Gson gson = new GsonBuilder().setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    }
}
