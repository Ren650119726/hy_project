
package com.alipay.sign;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSA{
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	
	public static void main(String args[]){
		RSA rsa = new RSA();
//		String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMlwXVTMPWpN0XAanjyWv9chc6iA1hMbCkyTkNTOtoRfNJv+b/Sw3mwzFp19Hb1pHYPtbmuL4F9gGyUNEG0OWOHTJlu1A9LcoEIl/dC2uDCock6zNRXqyVJiz2cSeFyNL++GK1G09ZbOvOFF3bmHEUA+XUaNXcEggg5eeeET8i5nAgMBAAECgYBP0e+Zt2iqo/VWt6V2GsIfzAiZf5vUbEWTfHHKcbLPFy6wlYzlZ+Rq+X9/nmFXebyRV1cG1KFvCZUYBGo63JGJDbykk3gZ9XhN7/UjUDHoeX/dtInDGQ38XqkUF6nNZnEcjbjSDhF25CxI1XErfirXsf4XI3hflNBdFdsJs9cLsQJBAPi083/93pz5QY+Sew4jXO5wLf0lxFchEqo7WiAeOiChyHJ41x9hLVD6x6fZarqOtnTQDWv/duZsgbjUpTPWqM8CQQDPWJJoYFPBm0b4b4qQCHGgZORi+46g7NYHK8asFgF/CKgq+lXYUAm8L8Trugd0MIqgedJOjUq81wqZcK0Gk1bpAkBL5ezpgnANN4PqbKlhBym1Gkbumjfw3wbzLGm0o4TZsIWbaYU87ksGC4IilxcbF7JWs6dk7fE7IKH15e8Egt3BAkADfVshdJWUSZe9MKtlVy8KYwhQ/ijdXn8PqdXzKX+m8q9Z4dK2DErNpwQqb3wWoYaDwukeN1SNrxTXlkTjOnGZAkABR9nVW6igVuyL+n3T/4FDl36+IWuCewgtIjhd1V9fOqG5NWHFIYxdJWHxjxyHyXAPtfssjzmj/v8rk4WfLfhy";
		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANdFqkTWuulTwfahE7396w+/RdNDjTVzinc1LdyWpkPaFRM9vepkpQ8cNmjSK+WMi2XiXNkO4QgcLhL4Fl5WJz/yjfuynUmc5LcMLtSFx5v3o6cjcptDx3xupdEIbnh3ILMsnQg86KDhEjWOndCw4fcwk27DK/j6oeojQ8m6jc7fAgMBAAECgYB7HzoaMyImDySRlWEAOjj2AbTC9xDNcG7SbVT0pCkmB5YkMhzr8XJCKSWpYAOBP5f+xq/J6WulQtDLQp8wh1/cc6nOkJcOVYIwuCe+BuOISLxR3gavykGb5CpzopPKddkLBXzs6eNunsDvew5EunSeBe5NvGCeyOvMM2w/aFZ1sQJBAOuxtw2/GOI/sZawtD/nyApIdAZmVxFoi7/39WwDPpKX2o4KaaOKemS/oFoMh3kK2G2d/usMiQ6Ig3hZ9xGfg7cCQQDp0Yl6fellKqMipP41Z2k+lBgj73Pv698OZxpV2dA7Xx4BlYv4AWRQcAR7lAo1aBQftiXTT+1qW2jtQS78yZ4ZAkEAyfxPB/TQ+nPjWWSt1GZqRIW7CREDZOPXf/ib1qkhIrgIkNnTllfCdDzUytcZ8l2dBLP2FrFzc4UCRk2zMuMlGQJBAJSEgCrKJ6IW/yBWZRJO6U7SirLoul+/FyPy4Yt+mwHbXRfsgce6OpnNTUuhsQpTXdT2wfgDYO6ZuYBaUxpGhqECQCVkWIA9iF3L89DSShuQVwr0EZLUNppj0DRFAwO2T5pRNWnWDzXlhrvjH0/uMZ0u4/38hSo+OzDl07gd9qeepbU=";
		
		String sign = rsa.sign("app_id=100003657&format=json&mer_id=100003657&order_no=38699_38757_4563&refund_amt=0.10&refund_no=6084&service=sumpay.trade.order.refund&terminal_info=mobile&terminal_type=mobile&timestamp=20160128163355&version=1.0", privateKey, "utf-8");
		
		System.out.println("sign="+sign);
		
	}
	
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 商户私钥
	* @param input_charset 编码格式
	* @return 签名值
	*/
	public static String sign(String content, String privateKey, String input_charset)
	{
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();
            
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param ali_public_key 支付宝公钥
	* @param input_charset 编码格式
	* @return 布尔值
	*/
	public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	* 解密
	* @param content 密文
	* @param private_key 商户私钥
	* @param input_charset 编码格式
	* @return 解密后的字符串
	*/
	public static String decrypt(String content, String private_key, String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

	
	/**
	* 得到私钥
	* @param key 密钥字符串（经过base64编码）
	* @throws Exception
	*/
	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;
		
		keyBytes = Base64.decode(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		
		return privateKey;
	}
}
