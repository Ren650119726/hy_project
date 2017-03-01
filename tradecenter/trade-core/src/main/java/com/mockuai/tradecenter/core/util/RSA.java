
package com.mockuai.tradecenter.core.util;

import payment.alipay.Base64;

import javax.crypto.Cipher;

import org.apache.commons.codec.digest.DigestUtils;

import com.mockuai.tradecenter.core.config.MockuaiConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA{
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	
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
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( payment.alipay.Base64.decode(privateKey) );
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();

            return payment.alipay.Base64.encode(signed);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }

        return null;
    }

	public static void main(String args[]){
//		yangdongxiSignTest();
//		mockuaiSignTest();
		wxSignTest();
	}
	
	private static void wxSignTest(){
//		String sb = "appid=wx791233ecd4e71c3f&attach=91_40090_3999&bank_type"+
//				"=BOC_DEBIT&cash_fee=7600&fee_type=CNY&is_subscribe=Y&mch_id=1220845901&nonce_str"+
//				"=5K8264ILTKCH16CQ2502SI8ZNMTM67VS&openid=oc9Xijt__DS0SyGDGqGnUPxSkDWQ&out_trade_"+
//				"no=2015083100400904657685188272&result_code=SUCCESS&return_code=SUCCESS&time_end"+
//				"=20150831172810&total_fee=7600&trade_type=JSAPI&transaction_id=10079502222015083"+
//				"10755172159&key=48d15a39462fbe06f6391328ff685954";
		
		String sb="appid=wx791233ecd4e71c3f&attach=91_40090_3999&mch_id=1220845901&openid=oc9Xijt__DS0SyGDGqGnUPxSkDWQ&cash_fee=7600&fee_type=CNY"+
				"&time_end=20150831172810&bank_type=BOC_DEBIT&nonce_str"+
				"=5K8264ILTKCH16CQ2502SI8ZNMTM67VS&total_fee=7600&trade_type=JSAPI&result_code=SUCCESS&return_code=SUCCESS&is_subscribe=Y&out_trade_"+
				"no=2015083100400904657685188272&transaction_id=1007950222201508310755172159&key=48d15a39462fbe06f6391328ff685954";
			String sign = DigestUtils.md5Hex(sb).toUpperCase();
			System.out.println("sign="+sign);	
	}
	
	private static void mockuaiSignTest(){
		String paramSb="body=魔筷商品&buyer_email=18268836320&buyer_id=2088412252"+
				"944733&gmt_create=2015-08-26 17:53:02&gmt_payment=2015-08-26 17:53:03&is_total_f"+
				"ee_adjust=N&notify_id=0634f221583cd0d4aabe8d990950896a62&notify_time=2015-08-26 "+
				"17:53:03&notify_type=trade_status_sync&out_trade_no=93744_93747_56564&payment_ty"+
				"pe=1&price=0.01&quantity=1&seller_email=mockuai@aliyun.com&seller_id=20889118412"+
				"25245&subject=魔筷商品&total_fee=0.01&trade_no=2015082600001000730063557622&trade_st"+
				"atus=TRADE_SUCCESS&use_coupon=N";
		
		String sign="fM1S/8Uo64YP0UEJyC0Kk6zqc5Zh3iYMBNabAPzzrQ"+
					"fvzDZ24lL5tnMF+tv3Gwn3e7onG4mPYbv33fPqhNeZCfZgdomhTD3BnRSqK3sAy7yJbGeX6KCLHMUEvB"+
					"cCulJSu7PuxuHb5csS9c19YrQtNvZ3N89dSqE05cEoLRFELC8=";
		 String alipayPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkJvcPew7sWXECAwBWj1CKN6BA6dkjcd/z8mEJJHRdjfPZJmIele90u9sFOQmsfiMzD3rb8RdGg+KFAjIhIY93bEiGoYF7PlJsSRE/pLR8tnR+M1ltwt5wGwlgu01JUY7M4qmKtV4y3sYBSowgEN3xwWL+4kd92J4efR0DTvEcqQIDAQAB";
		 alipayPublicKey = MockuaiConfig.alipay_public_key;
		System.out.println(verify(paramSb,sign,alipayPublicKey,"utf-8"));	
	}
	
	private static void yangdongxiSignTest(){
		String paramSb ="body=洋东西商品&buyer_email=15842637113&buyer_id=2088"+
			"612184059745&discount=0.00&gmt_create=2015-08-31 16:02:01&gmt_payment=2015-08-31"+
			 "16:02:02&is_total_fee_adjust=N&notify_id=8788fa31c87ba176ceac2fc15df8c5af64&not"+
			"ify_time=2015-08-31 16:02:02&notify_type=trade_status_sync&out_trade_no=91_39783"+
			"_3994&payment_type=1&price=19.90&quantity=1&seller_email=zhifu@yangdongxi.com&se"+
			"ller_id=2088311997503550&subject=洋东西商品&total_fee=19.90&trade_no=20150831000"+
			"01000740076743645&trade_status=TRADE_SUCCESS&use_coupon=N";
		String sign = "Yetj+vYkk8SPu8pk"+
				"Irw903yWC/Eth9po99o82LJW03a1n6r3GNS7XEVar1cPZGa2umywutMY9n8Q0yU1rzHxC2cCg0qBPMz8"+
				"xvbiPSiwzLHqFg0KC/rk5Uj3IP13jaLOYZEGjSVMIhr8HMH30Qu/vq91PoScmQ1O1m25whx7x/A=";
		String alipayPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkJvcPew7sWXECAwBWj1CKN6BA6dkjcd/z8mEJJHRdjfPZJmIele90u9sFOQmsfiMzD3rb8RdGg+KFAjIhIY93bEiGoYF7PlJsSRE/pLR8tnR+M1ltwt5wGwlgu01JUY7M4qmKtV4y3sYBSowgEN3xwWL+4kd92J4efR0DTvEcqQIDAQAB";
		System.out.println(verify(paramSb,sign,alipayPublicKey,"utf-8"));
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
	        byte[] encodedKey = payment.alipay.Base64.decode(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );

			boolean bverify = signature.verify( payment.alipay.Base64.decode(sign) );
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

        InputStream ins = new ByteArrayInputStream(payment.alipay.Base64.decode(content));
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
