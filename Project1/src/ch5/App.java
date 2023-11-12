package ch5;

public class App {
    private int count = 0;


    public static void main(String[] args) {

        App app = new App();
        app.doWork();
    }

    /*
        We create two thread - t1 and t2
        sharing a variable count and incrementing it 10000 times

        Expectation is that when the program terminates, count value should be 20000
        But interestingly, it will give inconsistent value < 20000 each time the program terminates

        Why ?
        - incrementing count has steps involving
            - accessing the count variable
            - adding one to it
            - storing back the incremented value
        - so it both threads are accessing the variable count at the same time
          it is natural for an individual thread to not be aware of the actual value at that moment
          hence arriving at a problem of race condition and we do not want both the thread to access
          such shared variables at the same time to prevent inconsistent result

        Note: this issue cannot be solved by using volatile keyword, as fundamentally the issue
              is related to both threads accessing the variable at the same time, nothing to do
              with caching the variable
     */

    public void doWork() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count++;
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count++;
            }

        });

        t1.start();
        t2.start();

        /*
            if we skip using join() method, count value would be 0 or inconsistent
            as the main thread is printing out the value of count which might get
            executed before the threads t1 and t2 complete its execution.

            join() method
            - lets the current thread and its children thread to complete all its execution,
              then only program would continue further
            - so we let t1 and t2 to complete its execution and till then, the main thread
              is stopped and cannot run until it happens
         */
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("count = " + count);

    }
}