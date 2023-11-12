package ch6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorkerLock {

    private final Random random = new Random();
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();

    /*
        The solution is to maintain two separate locks to run the two methods
        because the methods - stageOne() and stageTwo() - are independent of
        each other.
        - we want to run the two methods simultaneously and not get locked
          by the same lock
     */

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    /*
        This function is responsible for adding numbers to list1
     */
    public void stageOne() {

        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list1.add(random.nextInt());

        }

    }

    /*
        This function is responsible for adding numbers to list2
     */
    public void stageTwo() {

        synchronized (lock2) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list2.add(random.nextInt());
        }
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
