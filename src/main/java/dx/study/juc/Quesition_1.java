package dx.study.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程轮番打印5次使结果为abcabcabcabcabc
 */
@SuppressWarnings("all")
public class Quesition_1 {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition c = lock.newCondition();
    private static Thread t1;
    private static Thread t2;
    private static Thread t3;
    public static void main(String[] args) {
        t3();
    }

    /**
     * waitNotify
     */
    public static void t1(){
        waitNotify waitNotify = new waitNotify(1,5);
        Thread t1 = new Thread(() -> {
            waitNotify.print("a",1,2);
        });
        Thread t2 = new Thread(() -> {
            waitNotify.print("b",2,3);
        });
        Thread t3 = new Thread(() -> {
            waitNotify.print("c",3,1);
        });
        t1.start();
        t2.start();
        t3.start();
    }

    /**
     * awaitSignal
     */
    public static void t2(){
        awaitSignal awaitSignal = new awaitSignal(1,5);
        Thread t1 = new Thread(() -> {
            awaitSignal.print("a",1,2);
        });
        Thread t2 = new Thread(() -> {
            awaitSignal.print("b",2,3);
        });
        Thread t3 = new Thread(() -> {
            awaitSignal.print("c",3,1);
        });
        t1.start();
        t2.start();
        t3.start();
    }

    /**
     * ParkUnpark
     */
    public static void t3(){
        ParkUnpark parkUnpark = new ParkUnpark();
        t1 = new Thread(()->{
            parkUnpark.print("a",t2);
        });
        t2 = new Thread(()->{
            parkUnpark.print("b",t3);
        });
        t3 = new Thread(()->{
            parkUnpark.print("c",t1);
        });
        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);
    }

    static class waitNotify{
        private int flag;
        private final int count;

        public void print(String str, int waitFlag, int nextFlag) {
            for (int x = 0; x < count; x++) {
                synchronized (this) {
                    while (flag != waitFlag) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.print(str);
                    flag = nextFlag;
                    this.notifyAll();
                }
            }
        }

        public waitNotify(int flag, int count) {
            this.flag = flag;
            this.count = count;
        }
    }

    static class awaitSignal{
        private int flag;
        private final int count;

        public void print(String str, int waitFlag, int nextFlag) {
            for (int x = 0; x < count; x++) {
                try {
                    lock.lock();
                    while (flag != waitFlag) {
                        try {
                            c.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.print(str);
                    flag = nextFlag;
                    c.signalAll();
                }finally {
                    lock.unlock();
                }
            }
        }

        public awaitSignal(int flag, int count) {
            this.flag = flag;
            this.count = count;
        }
    }

    static class ParkUnpark{
        public void print(String str, Thread next) {
            for (int x = 0; x < 5; x++) {
                LockSupport.park();
                System.out.print(str);
                LockSupport.unpark(next);
            }
        }
    }
}
