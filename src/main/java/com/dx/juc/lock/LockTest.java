package com.dx.juc.lock;

import org.junit.Test;

import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    private static Lock lock = new ReentrantLock();
    private static int count = 10000;
//    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(20);
//        for (int i = 0; i < 10000; i++) {
//            executorService.execute(()->{
//                try {
//                    if(lock.tryLock(1000,TimeUnit.MILLISECONDS)){
//                        try {
//                            if(count >0) count--;
//                        } finally {
//                            lock.unlock();
//                        }
//                    }
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        }
//        executorService.shutdown();
//        while(true){
//            if(executorService.isTerminated()){
//                System.out.println(count);
//                break;
//            }
//        }
//
//    }

    @Test
    public void test1() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(()->{
            System.out.println("子线程已启动");
            try {
                if(lock.tryLock()) {
                    System.out.println("子线程已获取到锁");
                    System.out.println("子线程阻塞");
                    condition.await();
                    System.out.println(lock.isHeldByCurrentThread());
                    System.out.println("子线程被唤醒或者中断");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }finally {
                lock.unlock();
                System.out.println("子线程释放锁");
            }
        },"子线程").start();
        Thread.sleep(1000);
        try{
            System.out.println("主线程尝试获取锁");
            if(lock.tryLock()){
                System.out.println("主线程已获取到锁");
            }
        }finally {
            lock.unlock();
            System.out.println("主线程已释放锁");
        }
    }
}
