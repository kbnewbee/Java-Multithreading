package ch11;

import java.util.LinkedList;
import java.util.Queue;

/*
    We make our own producer and consumer using low level synchronized code
    using wait() and notify() methods
 */
public class Processor {

    private Queue<Integer> queue = new LinkedList<>();
    private final int CAPACITY = 10;

    private Object lock = new Object();

    public void publish() throws InterruptedException {

        int val = 0;

        while (true) {
            synchronized (lock) {
                while (queue.size() == CAPACITY) {
                    lock.wait();
                }
                queue.offer(val);
                val++;
                lock.notify();
            }
        }

    }

    public void consume() throws InterruptedException {

        while (true) {
            synchronized (lock) {
                while (queue.size() == 0){
                    lock.wait();
                }

                System.out.println("queue size = " + queue.size());
                int ele = queue.remove();
                System.out.println("Element removed = " + ele);
                lock.notify();
            }

            Thread.sleep(500);
        }
    }
}