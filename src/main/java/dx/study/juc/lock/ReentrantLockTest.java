package dx.study.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock的基本使用
 */
public class ReentrantLockTest {
    private static final ReentrantLock lock = new ReentrantLock();
    private static boolean flag = false;
    private static final Object o = new Object();
    private static final Condition c = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
//        t1();
//        t2();
//        t3();
//        t4();
//        t5();
//        t6();
//        t7();
//        t8();
    }

    /**
     * ReentrantLock基本使用
     */
    public static void t1(){
        for(int x = 0;x<10;x++){
            new Thread(()->{
                try{
                    lock.lock();
                    System.out.println(Thread.currentThread().getName()+"获得锁");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    System.out.println(Thread.currentThread().getName()+"释放锁");
                    lock.unlock();
                }
            },"线程"+x).start();
        }
    }

    /**
     * 演示ReentrantLock的可重入特性
     */
    public static void t2() {
        try {
            lock.lock();
            System.out.println("t2");
            t2TestMethod();
        }finally {
            lock.unlock();
        }
    }
    public static void t2TestMethod() {
        try {
            lock.lock();
            System.out.println("t2TestMethod");
        }finally {
            lock.unlock();
        }
    }

    /**
     * 演示ReentrantLock的可中断锁特性
     */
    public static void t3() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                System.out.println("尝试获取锁");

                /*可中断锁 **/
                lock.lockInterruptibly();

                System.out.println("获取锁成功");
            } catch (InterruptedException e) {
                System.out.println("打断锁");
            } finally {
                lock.unlock();
            }
        },"t1");
        lock.lock();
        t1.start();
        Thread.sleep(1000);
        t1.interrupt(); //执行后抛出InterruptedException异常，并结束运行
    }

    /**
     * tryLock()方法，尝试获取锁,如果成功返回true反之为false，同样
     * tryLock(time),在给定时间内尝试获取锁，如果成功返回true反之为false，在等待期间可以被interrupt()打断
     */
    public static void t4(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"尝试获取锁");
                try {
                    if(lock.tryLock(2, TimeUnit.SECONDS)){
                        try {
                            System.out.println(Thread.currentThread().getName()+"获取到锁");
                            Thread.sleep(1000);
                        }catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } finally {
                            System.out.println(Thread.currentThread().getName()+"释放了锁");
                            lock.unlock();
                        }
                    }else {
                        System.out.println(Thread.currentThread().getName()+"未获取到锁");
                    }
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName()+"中断了锁");
                }
            }
        };
        Thread t1 = new Thread(task,"t1");
        Thread t2 = new Thread(task,"t2");
        t1.start();
        t2.start();
//        t2.interrupt();
    }

    /**
     * lock.isLocked();       // 锁是否被持有
     * lock.isHeldByCurrentThread(); // 当前线程是否持有锁
     * lock.getQueueLength(); // 等待锁的线程数
     * lock.getHoldCount();   // 当前线程重入次数
     */
    public static void t5(){
        try {
            System.out.println(lock.isLocked());
            System.out.println(lock.isHeldByCurrentThread());
            lock.lock();
            System.out.println(lock.isLocked());
            System.out.println(lock.isHeldByCurrentThread());
            System.out.println(lock.getQueueLength());
            System.out.println(lock.getHoldCount());
            try {
                lock.lock();
                System.out.println(lock.getHoldCount());
            }finally {
                lock.unlock();
            }
        }finally {
            lock.unlock();
        }
    }

    /**
     * wait-notify 解决多线程协作问题
     */
    public static void t6(){
        Thread t1 = new Thread(()->{
            synchronized (o){
                while(!flag){
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(1);
            }
        },"t1");
        Thread t2 = new Thread(()->{
            synchronized (o){
                System.out.println(2);
                flag = true;
                o.notify();
            }
        },"t2");
        t1.start();
        t2.start();
    }

    /**
     * 解决多线程协作问题
     */
    public static void t7(){
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            System.out.println(1);
        });
        Thread t2 = new Thread(() -> {
            System.out.println(2);
            LockSupport.unpark(t1);
        });
        t1.start();
        t2.start();
    }

    /**
     * 使用Condition和ReentrantLock来解决多线程协作问题
     */
    public static void t8(){
        Thread t1 = new Thread(() -> {
            try{
                lock.lock();
                while (!flag){
                    c.await();
                }
                System.out.println(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });
        Thread t2 = new Thread(() -> {
            try{
                lock.lock();
                System.out.println(2);
                flag = true;
                c.signal();
            }finally {
                lock.unlock();
            }
        });
        t1.start();
        t2.start();
    }


}
