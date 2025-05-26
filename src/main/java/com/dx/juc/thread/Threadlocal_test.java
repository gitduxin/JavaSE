package com.dx.juc.thread;

public class Threadlocal_test {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                threadLocal.set(Thread.currentThread().getName());
                System.out.println(get());
            },"子线程"+i).start();
        }
    }

    public static String get() {
        return threadLocal.get();
    }
}
