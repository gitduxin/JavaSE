package com.dx.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadAndWrite {
    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock readLock = lock.readLock();
    private static Lock writeLock = lock.writeLock();
    static int count = 100;

    public static void read() {
        //System.out.println(Thread.currentThread().getName()+"尝试获取读锁");
        //readLock.lock();

        //readLock.unlock();
        //System.out.println(Thread.currentThread().getName()+"解除读锁");
    }

    public static void write() {

    }

    public static void main(String[] args) {
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"尝试获取写锁");
            try{
                writeLock.lock();
                count++;
                Thread.sleep(3000);
                count--;
                System.out.println(Thread.currentThread().getName()+"解除写锁");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                writeLock.unlock();
            }
        }).start();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"尝试获取读锁");
            try{
                readLock.lock();
                System.out.println(count);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                readLock.unlock();
                System.out.println(Thread.currentThread().getName()+"解除读锁");
            }
        }).start();
    }
}
