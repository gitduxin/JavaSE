package com.dx.juc.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool_test {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    public static SimpleDateFormat simpleDateFormat = dateFormatThreadLocal.get();
    public static Date date  = new Date();
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executorService.submit(()->{
                date.setTime(finalI * 1000);
                System.out.println(simpleDateFormat.format(date));
            });
        }
        executorService.shutdown();
        while(true){
            if(executorService.isTerminated()){
                long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
                break;
            }
        }
    }
}
