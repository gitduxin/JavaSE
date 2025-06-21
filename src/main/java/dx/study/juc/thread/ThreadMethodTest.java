package dx.study.juc.thread;

import org.junit.Test;

/**
 * Thread常用方法
 */
@SuppressWarnings("all")
public class ThreadMethodTest {
    /**
     * start方法
     * 开始执行该线程任务,将线程状态设置为 RUNNABLE
     * 若线程状态不为NEW ，则抛出 IllegalThreadStateException
     */
    @Test
    public void t1() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("线程已启动");
            }
        };
        Thread t = new Thread(r, "t1");
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
                for (int x = 0; x < 100000; x++) {
                    if (!Thread.currentThread().isInterrupted()) {
                        System.out.println(x);
                    }
                }
            }
        };
        Thread t = new Thread(r, "t1");
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
                System.out.println(Thread.currentThread().isInterrupted() + "---" + Thread.currentThread().isInterrupted());

                //返回true--false
                System.out.println(Thread.interrupted() + "---" + Thread.interrupted());
            }
        };
        Thread t = new Thread(r, "t1");
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
    public void t4() {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "_dosomething1");
            Thread.yield();
            System.out.println(Thread.currentThread().getName() + "_dosomething1");
        }, "t1").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "_dosomething1");
            Thread.yield();
            System.out.println(Thread.currentThread().getName() + "_dosomething1");
        }, "t2").start();
    }


    /**
     * sleep()方法，使线程休眠,该方法不会释放锁
     */
    @Test
    public void t5() throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("等待线程获取到锁");
                try {
                    // wait()会释放锁，允许其他线程进入同步块
                    System.out.println("等待线程开始休眠");
                    Thread.sleep(2000);
                    System.out.println("等待线程被唤醒，重新获取到锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                // 确保等待线程先执行
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                System.out.println("工作线程获取到锁");
                try {
                    Thread.sleep(1000); // 模拟工作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("工作线程释放锁");
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }


    /**
     * wait()方法，作用和sleep()方法类似,但wait()会释放锁
     * 它是一个object方法，执行该方法的条件是必须拥有对象锁
     */
    @Test
    public void t6() throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("等待线程获取到锁");
                try {
                    // wait()会释放锁，允许其他线程进入同步块
                    System.out.println("等待线程即将调用wait()释放锁...");
                    lock.wait(2000); // 等待2秒或直到被notify
                    System.out.println("等待线程被唤醒，重新获取到锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                // 确保等待线程先执行
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                System.out.println("工作线程获取到锁");
                try {
                    Thread.sleep(1000); // 模拟工作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("工作线程释放锁");
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
