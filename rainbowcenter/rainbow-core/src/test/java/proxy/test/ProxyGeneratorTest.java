package proxy.test;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * Created by zengzhangqiang on 9/21/15.
 */
public class ProxyGeneratorTest {
    public static void main(String[] args) throws Exception{
        try{
            byte[] classData = ProxyGenerator.generateProxyClass("CatDogImpl", CatDogImpl.class.getInterfaces());
            FileOutputStream fos = new FileOutputStream("/work/tmp/CatDogImpl.class");
            fos.write(classData);
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
