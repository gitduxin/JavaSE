package com.dx.juc.lock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 演示非公平锁情况下，读锁和写锁的插队情况
 */
public class ReadAndWrite_test2 {
    private static final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static final Lock readLock = lock.readLock();
    private static final Lock writeLock = lock.writeLock();

    public static void read(){
        System.out.println(Thread.currentThread().getName()+"尝试获取读锁");
        try {
            readLock.lock();
            //Thread.sleep(20);
            System.out.println(Thread.currentThread().getName()+"已获取读锁");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            readLock.unlock();

            System.out.println(Thread.currentThread().getName()+"已释放读锁");
        }
    }

    public static void write(){
        System.out.println(Thread.currentThread().getName()+"尝试获取写锁");
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName()+"已获取写锁");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName()+"已释放写锁");
        }
    }

    /**
     * 演示读锁插队情况
     * 前提：必须为非公平锁，排队队列头必须为读锁
     */
    public static void main(String[] args) {
        new Thread(ReadAndWrite_test2::write).start();
        new Thread(ReadAndWrite_test2::read).start();
        new Thread(ReadAndWrite_test2::read).start();
        new Thread(ReadAndWrite_test2::write).start();
        new Thread(ReadAndWrite_test2::read).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread [] threads = new Thread[1000];
                for (int i = 0; i < 1000; i++) {
                    threads[i] =  new Thread(ReadAndWrite_test2::read,"子线程"+i);
                }
                for (Thread thread : threads) {
                    thread.start();
                }
            }
        }).start();
    }

    /**
     * 演示写插队情况
     * 前提：必须为非公平锁，排队队列头必须为读锁
     */
//    public static void main(String[] args) throws InterruptedException {
//        Thread [] threads = new Thread[1000];
//        for (int i = 0; i < 1000; i++) {
//            threads[i] =  new Thread(ReadAndWrite_test2::read,"子线程"+i);
//        }
//        for (Thread thread : threads) {
//            thread.start();
//        }
//        new Thread(ReadAndWrite_test2::write).start();
//    }
}
