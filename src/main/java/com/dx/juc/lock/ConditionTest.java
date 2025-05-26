package com.dx.juc.lock;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {
    private int queueSize = 10;
    private PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(queueSize);
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    public void consume(){
        while (true){
            lock.lock();
            try{

            }catch (Exception e){

            }finally {

            }
        }
    }
}
