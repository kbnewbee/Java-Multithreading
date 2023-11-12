package ch8;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Introducing CountDownLatch which is like a latch on a
    specific number of threads.

    Use case
    - if a process requires a specific number of threads to be
      available, we use await() method of CountDownLatch to ensure
      that it releases that number of threads before starting the process
    - each time the threads complete the process, it calls the
      countdown() method to decrement the number of latches
    - when the countdown == 0, then only the latches would be released,
      meaning the threads would be released together to take a new task
 */

class Processor implements Runnable{
    private CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {

        System.out.println("Started *********** ");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        System.out.println("Completed *********** ");
        latch.countDown();
    }
}


public class App {

    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            executorService.submit(new Processor(latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
        }

        /*
            main thread would execute the below tasks only after the
            completion of the execution 3 threads above.
         */
        System.out.println("Completed all tasks");
    }
}
