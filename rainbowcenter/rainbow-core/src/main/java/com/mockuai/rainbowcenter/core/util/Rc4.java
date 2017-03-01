package com.mockuai.rainbowcenter.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * 描述:RC4加密解密算法
 * @author w520-3
 *
 */
public class Rc4 {  
	   
    public static String decry_RC4(byte[] data, String key) {  
        if (data == null || key == null) {  
            return null;  
        }  
        return asString(RC4Base(data, key));  
    }  
   
    public static String decry_RC4(String data, String key) { 
    	String rc4Str = "";
    	BufferedReader in = null;
        if (data == null || key == null) {  
            return null;  
        }  
        InputStream inputStr = new ByteArrayInputStream(RC4Base(HexString2Bytes(data), key));
        try {
			in = new BufferedReader(
					new InputStreamReader(inputStr,"UTF-8"));//一定要设置编码格式，否则会被浏览器请求的编码格式所影响
			String line;
			while ((line = in.readLine()) != null) {
				rc4Str += line;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(rc4Str);
        return rc4Str;  
    }  
   
    public static byte[] encry_RC4_byte(String data, String key) {  
        if (data == null || key == null) {  
            return null;  
        }  
        byte b_data[] = data.getBytes();  
        return RC4Base(b_data, key);  
    }  
   
    public static String encry_RC4_string(String data, String key) {  
        if (data == null || key == null) {  
            return null;  
        }  
        return toHexString(asString(encry_RC4_byte(data, key)));  
    }  
   
    private static String asString(byte[] buf) {  
        StringBuffer strbuf = new StringBuffer(buf.length);  
        for (int i = 0; i < buf.length; i++) {  
            strbuf.append((char) buf[i]);  
        }  
        return strbuf.toString();  
    }  
   
    private static byte[] initKey(String aKey) {  
        byte[] b_key = aKey.getBytes();  
        byte state[] = new byte[256];  
   
        for (int i = 0; i < 256; i++) {  
            state[i] = (byte) i;  
        }  
        int index1 = 0;  
        int index2 = 0;  
        if (b_key == null || b_key.length == 0) {  
            return null;  
        }  
        for (int i = 0; i < 256; i++) {  
            index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;  
            byte tmp = state[i];  
            state[i] = state[index2];  
            state[index2] = tmp;  
            index1 = (index1 + 1) % b_key.length;  
        }  
        return state;  
    }  
   
    private static String toHexString(String s) {  
        String str = "";  
        for (int i = 0; i < s.length(); i++) {  
            int ch = (int) s.charAt(i);  
            String s4 = Integer.toHexString(ch & 0xFF);  
            if (s4.length() == 1) {  
                s4 = '0' + s4;  
            }  
            str = str + s4;  
        }  
        return str;// 0x表示十六进制  
    }  
   
    private static byte[] HexString2Bytes(String src) {  
        int size = src.length();  
        byte[] ret = new byte[size / 2];  
        byte[] tmp = src.getBytes();  
        for (int i = 0; i < size / 2; i++) {  
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);  
        }  
        return ret;  
    }  
   
    private static byte uniteBytes(byte src0, byte src1) {  
        char _b0 = (char) Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();  
        _b0 = (char) (_b0 << 4);  
        char _b1 = (char) Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();  
        byte ret = (byte) (_b0 ^ _b1);  
        return ret;  
    }  
   
    private static byte[] RC4Base(byte[] input, String mKkey){  
        int x = 0;  
        int y = 0;  
        byte key[] = initKey(mKkey);  
        int xorIndex;  
        byte[] result = new byte[input.length];  
   
        for (int i = 0; i < input.length; i++) {  
            x = (x + 1) & 0xff;  
            y = ((key[x] & 0xff) + y) & 0xff;  
            byte tmp = key[x];  
            key[x] = key[y];  
            key[y] = tmp;  
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;  
            result[i] = (byte) (input[i] ^ key[xorIndex]);  
        } 
        return result;  
    }  
   
    public static void main(String[] args) {  
        //String inputStr = "{ \"data\": [ { \"orderSn\": \"YT1313320530259136952\", \"userName\": \"cs125963\", \"orderAmount\": \"680\", \"shippingFee\": \"5\", \"comCode\": \"youzhengguonei\", \"consignee\": \"董宝龙\", \"phoneMob\": \"18221185153\", \"address\": \"体育场路229号607室\", \"province\": \"浙江省\", \"city\": \"杭州市\", \"county\": \"下城区\", \"outTradeSn\": \"YT1313320530259136952\", \"tradeNum\": \"201512051315596315000108\", \"paymentId\": \"13\", \"outOrderGoods\": [ { \"onlySku\": \"310520141011500033\", \"price\": \"94\", \"quantity\": \"2\" }, { \"onlySku\": \"310520141011500171\", \"price\": \"160\", \"quantity\": \"3\" } ] } ] }";  
        //String inputStr = "{ \"data\": [ { \"orderSn\": \"YT1313320530259136959\", \"userName\": \"cs125963\", \"orderAmount\": \"680\", \"shippingFee\": \"5\", \"comCode\": \"youzhengguonei\", \"consignee\": \"董宝龙\", \"phoneMob\": \"18221185153\", \"address\": \"体育场路229号607室\", \"province\": \"浙江省\", \"city\": \"杭州市\", \"county\": \"下城区\",\"imId\": \"342901199003208596\",\"realName\": \"小鲜肉\", \"outTradeSn\": \"YT1313320530259136959\", \"tradeNum\": \"201512051315596315000109\", \"outOrderGoods\": [ { \"onlySku\": \"310520141011500033\", \"price\": \"94\", \"quantity\": \"2\" }, { \"onlySku\": \"310520141011500171\", \"price\": \"160\", \"quantity\": \"3\" } ] } ] }"; 
        String inputStr ="{\"data\":{\"beginTime\":\"1420819200\",\"endTime\":\"1452355200\"}}";
    	String str = encry_RC4_string(inputStr, "1c537672b9b2ac7d0886f041a4f2434b");  
        System.out.println(str);  
        System.out.println(decry_RC4(str, "1c537672b9b2ac7d0886f041a4f2434b"));  
    }  
}