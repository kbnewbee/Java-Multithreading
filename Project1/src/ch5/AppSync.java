package ch5;

public class AppSync {
    private int count = 0;

    /*
        We use synchronized functions to give access to this function to only
        one thread at a time by locking it when a thread is accessing the
        increment() method.

        How ?
        - Every object in Java has an intrinsic lock or monitoring lock - mutex
        - here app object acts as an intrinsic lock and either t1 or t2 at a given
          time can have access to this lock to access the increment() method
        - Meanwhile the other thread would have to wait waiting to acquire the lock
     */
    public synchronized void increment() {
        count++;
    }

    public static void main(String[] args) {

        AppSync app = new AppSync();
        app.doWork();
    }

    public void doWork() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }

        });

        t1.start();
        t2.start();

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
