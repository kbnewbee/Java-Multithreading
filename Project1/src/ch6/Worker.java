package ch6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {

    private final Random random = new Random();
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();

    /*
        This function is responsible for adding numbers to list1
     */
    public synchronized void stageOne() {

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        list1.add(random.nextInt());

    }

    /*
        This function is responsible for adding numbers to list2
     */
    public synchronized void stageTwo() {

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        list2.add(random.nextInt());
    }

    /*
        This function is responsible for calling
        stageOne() method and stageTwo() method
        1000 times
     */
    public void process() {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public void work() {
        System.out.println("hello from worker ......");

        /*
            We are trying to call the process() method simultaneously
            from two threads - t1 and t2

            So one thread would roughly take about 2s to complete the process
            math - 1 ms (sleep) * 1000 (iterations in for loop) = 2000 ms

            Since we run two threads simultaneously - it ideally should not affect the
            total time take, but in reality, due to inconsistent results because of sharing
            resources - list1 and list2, we have to make the methods
            - stageOne() and stageTwo() - synchronized
            - as a result, the program would take close to double the expected time = ~ 4s

            This defeats the purpose of running two threads at the same time.

        */

        long st = System.currentTimeMillis();

        Thread t1 = new Thread(this::process);
        t1.start();

        Thread t2 = new Thread(this::process);
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long en = System.currentTimeMillis();

        System.out.println("Time taken = " + (en - st));
        System.out.println("list1 size = " + list1.size());
        System.out.println("list2 size = " + list2.size());
    }

}
