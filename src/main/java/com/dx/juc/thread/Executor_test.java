package com.dx.juc.thread;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Executor_test extends ThreadPoolExecutor {

    private boolean ispaused;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public Executor_test(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        lock.lock();
        try{
            while (ispaused) {
                //System.out.println("触发中断信号");
                condition.await();
            }
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }

    }

    private  void pause(){
        lock.lock();
        try {
            ispaused = true;
        }finally {
            lock.unlock();
        }
    }

    private void unpause(){
        lock.lock();
        try {
            ispaused = false;
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Executor_test et = new Executor_test(10,20, 0,TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        for(int i=0;i<1000;i++){
            et.execute(() -> {
                System.out.println("我被执行了");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Thread.sleep(1500);
        et.pause();
        System.out.println("暂停！！！！！！！！！！！！！！！！");
        Thread.sleep(1500);
        et.unpause();
        System.out.println("启动！！！！！！！！！！！！！！！！");
    }
}
