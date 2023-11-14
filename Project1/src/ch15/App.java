package ch15;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

/*
    Callable and Futures - To solve the limitations of Runnable Interface

 */
public class App {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> future =
                executorService.submit(new Callable<Integer>() {

                    /*
                        call() method is analogous to run() method in Runnable Interface
                        - it can return a result of any type (not possible in run() method)
                        - this type is a generic type we can define in the Callable<T>()
                        - it can throw exception as well (not possible in run() method)
                        - use return type Void if you do not want to return anything
                     */
                    @Override
                    public Integer call() throws Exception {
                        Random random = new Random();
                        int dur = random.nextInt(4000);

                        if (dur > 1000) {
                            throw new IOException("It is taking too much time, so accept this exception");
                        }

                        System.out.println("Starting ..........");

                        Thread.sleep(dur);
                        System.out.println("Finished ..........");
                        return dur;
                    }
                });

        executorService.shutdown();

        /*
            Future is used to store the value returned by a callable
            - it is like a wrapper over the data that is returned
            - can be stored like an ArrayList<Future<Integer>> as well
              to handle results from multiple threads

            get() method extracts the value from the future
            - it is a blocking operation, so until the thread has
              finished execution, program will not proceed further
         */
        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
            // exception thrown from the call() method is handled here
            IOException ex = (IOException) e.getCause();
            System.out.println(e.getMessage());
        }

        /*
            Some other useful methods in future
            - future.cancel() - cancel a thread from completing its execution
                              - If the task has already started, then the mayInterruptIfRunning parameter
                                determines whether the thread executing this task should be interrupted in an
                                attempt to stop the task
            - future.isCancelled() - to check whether an execution is cancelled or not
            - future.isDone() - to check whether an execution is completed or not
         */




    }
}
