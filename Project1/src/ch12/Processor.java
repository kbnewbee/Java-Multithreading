package ch12;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    This is an alternative method to synchronization to handle locks
    using ReentrantLock

 */
public class Processor {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    /*
        Condition is important if we want to use
        - await() method - analogous to wait() method
        - signal() method - analogous to notify() method
     */
    private Condition condition = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public void funcOne() throws InterruptedException {
        lock.lock();
        System.out.println("Waiting for the lock .......");
        condition.await();
        System.out.println("Got the lock back, resuming funcOne() ....");

        try {
            System.out.println("funcOne is incrementing .....");
            increment();
        } finally {
            /*
                unlock() method must be called irrespective of whether the
                function inside try is successful or not, because if it throws
                any exception, other threads would never be able to acquire the lock
             */
            lock.unlock();
        }
    }

    public void funcTwo() throws InterruptedException {
        Thread.sleep(2000);
        lock.lock();

        System.out.println("Press enter to continue funcTwo .....");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        System.out.println("Enter is pressed, resuming funcTwo .......");
        condition.signal();

        try {
            System.out.println("funcTwo is incrementing .....");
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void finish() {
        System.out.println("count = " + count);
    }
}
