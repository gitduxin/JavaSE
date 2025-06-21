package dx.study.juc.sync;

import org.junit.Test;

/**
 * Synchronized的基本使用
 * 一把锁只能同时被一个线程获取，没有获得锁的线程只能等待；
 * 每个实例都对应有自己的一把锁(this),不同实例之间互不影响；例外：锁对象是*.class以及synchronized修饰的是static方法的时候，
 * 所有对象公用同一把锁synchronized修饰的方法，无论方法正常执行完毕还是抛出异常，都会释放锁
 */

@SuppressWarnings("all")
public class SyncTest {

    /**
     * 修饰代码块
     * 手动指定锁定对象，可以是this,也可以是自定义的对象
     */
    @Test
    public void t1() throws InterruptedException {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                //synchronized (this) 也可以是 synchronized (o) /Object o = new Object();
                synchronized (this){
                    System.out.println("我是线程" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + "结束");
                }
            }
        };
        Thread t1 = new Thread(task,"t1");
        Thread t2 = new Thread(task,"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    /**
     * 修饰普通方法，锁对象默认是this
     *
     */
    @Test
    public void t2() throws InterruptedException {
        SyncTest o = new SyncTest();
        Thread t1 = new Thread(this::ptMethod,"t1");
        Thread t2 = new Thread(this::ptMethod,"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    /**
     * 等效于
     * public void ptMethod(){
     *     synchronized(this) {
     *
     *     }
     * }
     */
    public synchronized void ptMethod(){
        System.out.println("我是线程" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "结束");
    }

    /**
     * 修饰静态方法时或锁对象是class时，将对类的所有实例进行加锁
     */
    @Test
    public void t3() throws InterruptedException {
        Thread t1 = new Thread(SyncTest::staticMethod,"t1");
        Thread t2 = new Thread(SyncTest::staticMethod,"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    /**
     * 等效于
     * public static void staticMethod(){
     *     synchronized(SyncTest.class) {
     *
     *     }
     * }
     */
    public synchronized static void staticMethod(){
        System.out.println("我是线程" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "结束");
    }

}
