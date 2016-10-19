package com.suyun.vehicle;

/**
 * Created by IT on 16/9/19.
 */
public class Test1 {

    public void test1() {
        Float s = new Float(0.9F);
        Float t = new Float(0.9F);
        Double u = new Double(0.9);

    }
}

class Foo implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            System.out.print(i);
        }
    }

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Foo());
        t.start();
        System.out.print("Start");
        t.join();
        System.out.print("Complete");
    }
}