package com.cat.proxy.dynamic.cglib;

import java.util.Random;

/**
 * Created by cat on 2017/9/10.
 */
public class Train {

    public boolean move(String road) {

        System.out.println("I am running in road: " + road);
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextInt() % 2 == 0;
    }
}
