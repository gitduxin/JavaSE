package com.dx.juc.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MyCountDown {

    private final Sync sync;

    public MyCountDown(int count) {
        this.sync = new Sync(count);
    }

    public void await(){
        sync.acquireShared(1);
    }

    public void countDown(){
        sync.releaseShared(1);
    }

    private class Sync extends AbstractQueuedSynchronizer{

        public Sync(int count) {
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return (getState() == 0) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;) {
                int c = getState();
                if (c == 0)
                    return false;
                int nextc = c-1;
                if (compareAndSetState(c, nextc))
                    return nextc == 0;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyCountDown myCountDown = new MyCountDown(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "开始执行");
                try {
                    Thread.sleep((long) (Math.random() * 10000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    myCountDown.countDown();
                }
                System.out.println(Thread.currentThread().getName() + "运行结束");
            }).start();
            myCountDown.await();
            System.out.println("所有子线程运行完毕");
        }
    }
}
