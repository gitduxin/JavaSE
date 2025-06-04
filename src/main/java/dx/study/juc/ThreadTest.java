package dx.study.juc;

import org.junit.Test;

/**
 * 线程问题测试
 */
public class ThreadTest {
    private static boolean flag = true;
    private static int count = 0;
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;


    /**
     * 由于现代cpu架构中多核心的存在，每个核心拥有自己的缓存
     * 线程1读取变量时，将变量从主内存中读取到cpu内存中
     * 若线程2在其他cpu核心运行，修改了该变量，因线程1因为cpu的缓存的存在并不会去读取主内存中最新的值，而是使用了cpu缓存中值，导致了两者不一样
     * 从而带来了可见性的问题
     */
    @Test
    public void t1() throws InterruptedException {
        Runnable task1 = () -> {
            System.out.println("Thread 1 started");
            while (flag) {
                // 空循环
            }
            System.out.println("Thread 1 finished");
        };
        Runnable task2 = () -> {
            System.out.println("Thread 2 changing flag to false");
            flag = false;
        };
        Thread t1 = new Thread(task1,"t1");
        Thread t2 = new Thread(task2,"t2");
        t1.start();
        Thread.sleep(1000);
        t2.start();
        t1.join();
        t2.join();
    }

    /**
     * 原子性指的是一个操作是不可中断的，要么全部执行成功，要么全部不执行。在多线程环境下
     * 非原子操作可能会被其他线程中断，导致数据不一致。
     */
    @Test
    public void t2() throws InterruptedException {
        Runnable task1 = () -> {
            for(int x=0;x<10000;x++){
                count++;
            }
        };
        Runnable task2 = () -> {
            for(int x=0;x<10000;x++){
                count--;
            }
        };
        Thread t1 = new Thread(task1,"t1");
        Thread t2 = new Thread(task2,"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(count);
    }

    /**
     * 有序性指的是程序执行的顺序按照代码的先后顺序执行。
     * 由于指令重排序优化（编译器、处理器），代码的实际执行顺序可能与编写顺序不一致，导致在多线程环境下出现意想不到的结果。
     */
    @Test
    public void t3() throws InterruptedException {
        int i = 0;
        while (true) {
            i++;
            x = 0; y = 0;
            a = 0; b = 0;

            Thread t1 = new Thread(() -> {
                a = 1;
                x = b;
            });

            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
            });

            t1.start();
            t2.start();
            t1.join();
            t2.join();

            if (x == 0 && y == 0) {
                System.out.println("第 " + i + " 次循环时发生了指令重排序");
                break;
            }
        }
    }
}
