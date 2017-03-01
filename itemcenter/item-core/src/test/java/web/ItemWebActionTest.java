package web;

import com.mockuai.itemcenter.core.util.HttpUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yindingyu on 15/9/28.
 */
public class ItemWebActionTest {


    private String requestPath="http://localhost:8080/item/queryItems.do";

    @Test
    public void Test01(){
        String reqUrl = requestPath;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("itemDTO", "{}"));
        String response = HttpUtil.post(reqUrl, params);
        System.out.println("response:" + response);

    }
    @Test
    public void test2(){
        Integer gainSet = 10;
        Long price = 600L;
        Double dPrice = Double.valueOf(price);
        Double d = Double.valueOf(dPrice /gainSet /100);
        System.out.print(d);
    }

}
