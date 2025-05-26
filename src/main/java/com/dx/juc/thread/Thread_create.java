package com.dx.juc.thread;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 线程创建的几种方式
 */
public class Thread_create {

    /**
     *  1.直接创建Thread类，并且重写run方法
     */
    @Test
    public void createThread(){
        Thread thread = new Thread("t1"){
            public void run(){
                System.out.println(Thread.currentThread().getName()+":do something......");
            }
        };
        thread.start();
    }


    /**
     * 2.实现Runnable接口，并将该任务交给Thread执行
     */
    @Test
    public void createRunnable(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+":do something......");
            }
        };
        Thread thread = new Thread(runnable,"t1");
        thread.start();

        //lambda简化
        //new Thread( () -> System.out.println("do something......") ).start();
    }

    /**
     * 3.
     */
    @Test
    public void test3() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int count = 0;
                for(int i=0; i<1000000000; i++){
                    count ++;
                }
                System.out.println(Thread.currentThread().getName()+":do something......");
                return count;
            }
        });
        Thread t1 = new Thread(task,"t1");
        t1.start();
        System.out.println("主线程继续执行1");
        System.out.println(task.get());
        System.out.println("主线程继续执行2");
    }

}
