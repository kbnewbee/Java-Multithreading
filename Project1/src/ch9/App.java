package ch9;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
    Amazing way to handle producer consumer problem of sharing resource
    - Java introduces BlockingQueue for handling the issue
    - It is thread safe, so do not worry about low level handling of synchronization
 */

public class App {

    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    private static void publish() throws InterruptedException {
        Random random = new Random();

        while (true) {
            /*
                put() method
                - adds data into the queue
                - will wait if the queue is full
             */
            queue.put(random.nextInt(100));
        }
    }

    private static void consume() throws InterruptedException {

        Random random = new Random();
        while (true) {
            Thread.sleep(100);

            if (random.nextInt(10) == 0) {

                /*
                    take() method
                    - polls data from the queue
                    - will wait if the queue is empty
                 */
                Integer val = queue.take();

                System.out.println("consumed = " + val);
                System.out.println("queue size = " + queue.size());
            }
        }
    }


    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                publish();
            } catch (InterruptedException e) {
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                consume();
            } catch (InterruptedException e) {
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        }
    }
}
