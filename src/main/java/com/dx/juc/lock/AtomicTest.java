package com.dx.juc.lock;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < 10000; i++) {
            new Thread(atomicInteger::incrementAndGet).start();
        }
        System.out.println(atomicInteger.get());
    }
}
