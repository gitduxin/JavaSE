package dx.study.juc;

import org.junit.Test;

/**
 * 线程问题测试
 */
public class ThreadTest {
    private static int count = 0;

    /**
     *由于多核心cpu和每个cpu的独立缓存的存在
     *多个线程对一个共享变量同时进行修改
     */
    @Test
    public void t1() throws InterruptedException {
        Runnable task = () -> {
            for(int x=0;x<10000;x++){
                count++;
            }
        };
        Thread t1 = new Thread(task,"t1");
        Thread t2 = new Thread(task,"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(count); // count < 20000
    }
}
