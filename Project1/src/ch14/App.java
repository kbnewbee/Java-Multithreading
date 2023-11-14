package ch14;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/*
    Introducing Semaphores - Maintain the count of permits
    - release() - releases a permit back - increases the number of permits in semaphore
    - acquire() - acquires a permit from - decreases the number of permits in semaphore

    - theses two functions can be used as a lock without worrying about synchronization

        Semaphore semaphore = new Semaphore(1);
        semaphore.release();
        semaphore.acquire();
        System.out.println("Available permits = " + semaphore.availablePermits());

 */
public class App {

    public static void main(String[] args) throws InterruptedException {


        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 200; i++) {
            executorService.submit(() -> {
                Connection.getConnectionInstance().connect();

            });
        }

        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.HOURS);
    }
}
