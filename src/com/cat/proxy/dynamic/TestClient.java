package com.cat.proxy.dynamic;

import javax.xml.transform.Source;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created by cat on 2017/9/10.
 */
public class TestClient {

    public static void main(String[] args) {
        Moveable m = new Car();

        Class<? extends Moveable> clazz = m.getClass();

        Moveable proxyInstance = (Moveable) Proxy.newProxyInstance(clazz.getClassLoader(),
                clazz.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        System.out.println("do before >>>>");

                        long st = System.currentTimeMillis();
                        Object invoke = method.invoke(m, args);
                        long et = System.currentTimeMillis();
                        System.err.println("invoke = " + invoke + " , " + method + " , " + Arrays.toString(args));
                        System.out.println("do after <<<<<");

                        System.out.println(method.getName() + " 耗时： " + (et - st));
                        return invoke;
                    }
                });

        proxyInstance.move("Tokyo");

    }
}
