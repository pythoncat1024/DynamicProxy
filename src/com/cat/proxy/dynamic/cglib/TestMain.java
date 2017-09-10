package com.cat.proxy.dynamic.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

/**
 * Created by cat on 2017/9/10.
 */
public class TestMain {


    public static void main(String[] args) throws InterruptedException {

        Train train = new Train();
        proxy1(train);
        Thread.sleep(400);
        System.err.println(" x1 再来一次 ################");
        proxy2(train);
        Thread.sleep(400);
        System.err.println(" x2 再来一次 ################");

        proxy3(train);

    }

    private static void proxy3(Train train) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(train.getClass());
        enhancer.setCallback((MethodInterceptor) (target, method, objects, methodProxy) -> {
            System.out.println("** do before >>> .");
//            Object invokeSuper = methodProxy.invokeSuper(target, objects);
            Object invokeSuper = method.invoke(train, objects); // 使用 method 和 methodProxy 都可以。
            System.err.println((target instanceof Train) + " , " + method + " , " + Arrays.toString(objects) + " , " + methodProxy);
            System.out.println("** do after <<<< $ ");
            return invokeSuper;
        });
        Train trainProxy = (Train) enhancer.create();
        trainProxy.move("Paris");
    }

    // 一步完成 cglib 的动态代理
    private static void proxy2(Train train) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(train.getClass());
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("** do before >>>");
                Object invokeSuper = methodProxy.invokeSuper(o, objects);
                System.err.println((o instanceof Train) + " , " + method + " , " + Arrays.toString(objects) + " , " + methodProxy);
                System.out.println("** do after <<<<");
                return invokeSuper;
            }
        });

        Train trainProxy = (Train) enhancer.create();

        trainProxy.move("NewYork");
    }

    // 分步完成 cglib 代理类
    private static void proxy1(Train train) {
        CglibProxy proxy = new CglibProxy();

        Train proxyProxy = (Train) proxy.getProxy(train.getClass());

        proxyProxy.move("Tokyo");
    }
}
