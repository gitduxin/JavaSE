package com.dx.juc.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest extends Thread{
    private Object object;

    public LockSupportTest(Object object) {
        this.object = object;
    }

    public static void main(String[] args) throws InterruptedException {
        LockSupportTest lockSupportTest = new LockSupportTest(Thread.currentThread());
        System.out.println("主线程已启动");
        lockSupportTest.start();
        System.out.println("主线程已阻塞");
        LockSupport.park();
        System.out.println("主线程已唤醒111111111111111111");

    }

    @Override
    public void run() {
        System.out.println("子线程启动");
        System.out.println("子线程唤醒主线程");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //LockSupport.unpark((Thread) object);
        Thread thread = (Thread) object;
        thread.interrupt();
        System.out.println("主线程已唤醒");
    }
}
