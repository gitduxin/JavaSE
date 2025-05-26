package com.dx.juc.thread;

import org.junit.Test;

/**
 * Thread类各种方法
 */
public class Thread_method {

    @Test
    public void test1() {
        Thread thread = new Thread("t1"){

            @Override
            public void run() {
                while(!isInterrupted()){
                    System.out.println(this.getName());
                }
                System.out.println("end");
            }
        };
        thread.start();
        thread.interrupt();
    }

    @Test
    public void test2(){
        Thread thread1 = new Thread("t1"){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("t1");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread1.start();
//        Thread thread2 = new Thread("t2"){
//            @Override
//            public void run() {
//                System.out.println("t2");
//            }
//        };
//        thread2.start();
    }
}
