package ch7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
    This is a Processor which needs to be executed many times simultaneously
    - it has an id and a constructor
    - each process takes 5s to run

    We do not want to create new threads for each process, hence we use the
    concept of reusing threads.
 */
class Processor implements Runnable {

    private int id;

    public Processor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Starting = " + id);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed = " + id);
    }
}

public class App {
    public static void main(String[] args) {

        /*
            In order to reuse threads, Java provides ExecutorService interface
            to manage a fixed pool or set of threads to allocate tasks

            We create a thread pool consisting of 3 threads to perform 7 tasks.
         */

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        /*
            submit 7 tasks or instances of Processors to the executorService
            which in turn will allocate the tasks to the threads available from the pool
        */
        for (int i = 0; i < 7; i++) {
            executorService.submit(new Processor(i));
        }

        System.out.println("All tasks submitted to the executor service");

        try {
            /*
                awaitTermination() will terminate all the threads belonging to
                the pool after it times out, irrespective of whether the thread
                finished execution or not
            */
            executorService.awaitTermination(2, TimeUnit.DAYS);
        } catch (InterruptedException e) {
        }

        System.out.println("All tasks completed");

    }
}

