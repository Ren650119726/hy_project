package test.order;


import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Dubbo服务启动
 * 
 * @author Kerwin
 * @date 2013-10-24 下午08:52:35
 */
public class DubboTest {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();


        System.out.println("stared...");
        System.in.read();
    }
}
