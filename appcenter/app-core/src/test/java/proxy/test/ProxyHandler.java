package proxy.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zengzhangqiang on 9/21/15.
 */
public class ProxyHandler implements InvocationHandler{
    private Object target;

    public ProxyHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("pre handle...");
        Object result = method.invoke(target, args);
        System.out.println("after handle...");
        return result;
    }

    public static void main(String[] args){
        CatDogImpl catDog = new CatDogImpl();
        Cat catProxy = (Cat)Proxy.newProxyInstance(catDog.getClass().getClassLoader(),
                catDog.getClass().getInterfaces(), new ProxyHandler(catDog));

        catProxy.call();
    }
}
