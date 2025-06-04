package dx.study.juc;

import org.junit.Test;

/**
 * Thread常用方法
 */
public class ThreadMethodTest {
    public static void main(String[] args) {

    }


    /**
     * start方法
     * 开始执行该线程任务,将线程状态设置为 RUNNABLE
     * 若该线程状态不为 NEW ，则抛出 IllegalThreadStateException
     */
    @Test
    public void t1(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("线程已启动");
            }
        };
        Thread t = new Thread(r,"t1");
        t.start();
        //t.start(); //IllegalThreadStateException
    }


    /**
     * interrupt方法
     * 设置中断标记
     * 如果线程正在 wait()、sleep() 或 join() 等可中断的阻塞状态，会抛出 InterruptedException（同时清除中断标志）
     */
    @Test
    public void t2() throws InterruptedException {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for(int x=0;x<100000;x++){
                    if(!Thread.currentThread().isInterrupted()){
                        System.out.println(x);
                    }
                }
            }
        };
        Thread t = new Thread(r,"t1");
        t.start();
        Thread.sleep(10);
        t.interrupt();
        t.join(); // 等待线程t执行完毕
    }


    /**
     * Thread.isInterrupted()方法，判断线程是否拥有中断标记，有返回ture
     * Thread.interrupted()方法，同样也是判断线程是否拥有中断标记，返回结果并且清除中断标记
     */
    @Test
    public void t3() throws InterruptedException {
        Runnable r = new Runnable() {
            @Override
            public void run() {

                //返回true--true
                System.out.println(Thread.currentThread().isInterrupted()+"---"+Thread.currentThread().isInterrupted());

                //返回true--false
                System.out.println(Thread.interrupted()+"---"+Thread.interrupted());
            }
        };
        Thread t = new Thread(r,"t1");
        t.start();
        t.interrupt();
        t.join(); // 等待线程t执行完毕
    }


    /**
     * yield方法
     * 提示调度器当前线程愿意让出CPU
     * 让相同或更高优先级的线程有机会运行
     * 这是一个静态方法，只能影响当前执行的线程
     */
    @Test
    public void t4(){
        new Thread(()-> {
            System.out.println(Thread.currentThread().getName()+"_dosomething1");
            Thread.yield();
            System.out.println(Thread.currentThread().getName()+"_dosomething1");
        },"t1").start();
        new Thread(()-> {
            System.out.println(Thread.currentThread().getName()+"_dosomething1");
            Thread.yield();
            System.out.println(Thread.currentThread().getName()+"_dosomething1");
        },"t2").start();
    }
}
