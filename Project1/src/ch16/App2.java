package ch16;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class App2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting app2........");

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?> future = executorService.submit(() -> {
            Random random = new Random();
            for (int i = 0; i < 1E8; i++) {

                // different way of checking interruption compared to try/catch block
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Someone interrupted");
                    break;
                }

                Math.sin(random.nextDouble());
            }

            return null;
        });

        executorService.shutdown();

        Thread.sleep(500);

        /*
            future.cancel(false) - nothing will change
            future.cancel(true) - will cause interruption
         */
        future.cancel(true);

        executorService.awaitTermination(1, TimeUnit.HOURS);

        System.out.println("Finished app2........");
    }
}
