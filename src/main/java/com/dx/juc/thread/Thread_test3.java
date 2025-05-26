package com.dx.juc.thread;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class Thread_test3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(service.submit(() -> new Random().nextInt()));
        }
        service.shutdown();
        for (Future<Integer> future : futures) {
            System.out.println(future.get());
        }
    }
}
