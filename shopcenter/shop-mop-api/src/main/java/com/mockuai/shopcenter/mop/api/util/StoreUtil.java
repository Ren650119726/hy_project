package com.mockuai.shopcenter.mop.api.util;

/**
 * Created by yindingyu on 15/11/4.
 */
public class StoreUtil {

    private static final String UNDERLINE = "_";

    public static String genUid(Long id,Long sellerId){

        if(id ==null || sellerId  == null){
            return null;
        }
        return String.valueOf(sellerId) + UNDERLINE + String.valueOf(id);
    }

    public static Long getId(String uid){

        if(uid == null){
            return null;
        }

        String[] strs = uid.split(UNDERLINE);

        if(strs.length>1){
            String str =  strs[1];

            try {
              Long id =   Long.valueOf(str);
                return id;
            }catch (Exception e){
                return null;
            }
        }

        return null;
    }

    public static Long getSellerId(String uid){

        if(uid == null){
            return null;
        }

        String[] strs = uid.split(UNDERLINE);

        if(strs.length>0){
            String str =  strs[0];

            try {
                Long sellerId =   Long.valueOf(str);
                return sellerId;
            }catch (Exception e){
                return null;
            }
        }

        return null;
    }
}
