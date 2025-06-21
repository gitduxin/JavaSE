package dx.study.juc.thread;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/***
 * 线程的实现方式
 */

@SuppressWarnings("all")
public class ThreadCreateTest {

    public static void main(String[] args) {

    }

    /**直接创建Thread类，并且实现run方法*/
    @Test
    public void t1(){
        Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println(this.getName()+"--创建thread类,并且实现run方法");
            }
        };
        System.out.println(Thread.currentThread().getName());
        t1.start();
    }


    /**
     * 创建Threa类，并且将传递一个task任务
     * 其本质和第一种没什么区别，两者都是传递了一个task
     */
    @Test
    public void t2(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("创建Runnable任务，并且实现该接口的run方法");
            }
        };
        Thread t1 = new Thread(task);
        t1.start();
    }

    /**
     * 实现Callable接口，和上述两种没有什么区别，唯一一点就是可带返回值
     */
    @Test
    public void t3() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            int count = 0;
            for(int i=0; i<1000000000; i++){
                count ++;
            }
            System.out.println(Thread.currentThread().getName()+":do something......");
            return count;
        });
        Thread t1 = new Thread(task,"t1");
        t1.start();
        System.out.println("主线程继续执行1");
        System.out.println(task.get());
        System.out.println("主线程继续执行2");
    }

}

