package com.mockuai.rainbowcenter.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.core.config.RainbowConfig;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

import java.util.*;

/**
 * Created by yeliming on 16/3/9.
 */
public class ImageUploadUtil {
    public static Boolean doUpload(String url, Map<String, String> imageUrls, String bizCode) throws RainbowException {

        Map<String, String> newEntry = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> entry : imageUrls.entrySet()) {
            if (entry.getValue() != null) {
                newEntry.put(entry.getKey(), entry.getValue());
            }
        }

        Iterator<Map.Entry<String, String>> iterator = newEntry.entrySet().iterator();
        List<String> tmpArrayImage = new ArrayList<String>();
        int current = 0;
        for (Map.Entry<String, String> entry : newEntry.entrySet()) {
            tmpArrayImage.add(entry.getValue());
            if (tmpArrayImage.size() % 3 == 0 || ++current == newEntry.size() - 1) {
                Map<String, String> params = new HashMap<String, String>();
                String json = JSON.toJSONString(tmpArrayImage.toArray(new String[]{}));
                params.put("image_path", json);
                params.put("biz_code", bizCode);
                String response = HttpUtil.doPost(url, params);
//                String response = "{\"code\":10000,\"message\":\"操作完成，成功3个,失败0个\",\"data\":[{\"url\":\"1\"},{\"url\":\"2\"},{\"url\":\"3\"}]}";
                JSONObject root = JSONObject.parseObject(response);
                if (root.getInteger("code") == 10000) {
                    JSONArray array = root.getJSONArray("data");
                    for (Object o : array) {
                        Map.Entry<String, String> tmpEntry;
                        if (iterator.hasNext() && (tmpEntry = iterator.next()).getValue() != null) {
                            tmpEntry.setValue(((JSONObject) o).getString("url"));
                        }
                    }
                } else {
                    throw new RainbowException(ResponseCode.SYS_E_IMAGE_UPLOAD_EXCEPTION);
                }
                tmpArrayImage.clear();
            }

            imageUrls.putAll(newEntry);
        }

        return true;

    }

    public static void main(String... args) throws Exception {
        Map<String, String> imageUpload = new LinkedHashMap<String, String>();
        imageUpload.put("idcardfront", null);
        imageUpload.put("bussinesslicense", "http://www.gsxzb.com/english/images/gsx1.jpg");
        imageUpload.put("organization", "http://img.atobo.com/UserFiles/Certificate/1/7/5/4/588/1754588/Big/2011_2_12_13_55_2_456.jpg");
        imageUpload.put("tax", "http://www.un188.com/textqi/upupload/2010914105257208.jpg");

        doUpload(RainbowConfig.UPLOAD_URL, imageUpload, "rainbow_test");
    }
}
