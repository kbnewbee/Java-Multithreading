package ch1;


/*
    Method 1 of creating a thread : extending the Thread class

    sleep() method stops the execution of the current running thread
    for a specified time in milliseconds
*/

class MyThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("hello " + i);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class App {

    public static void main(String[] args) {

        /*
            We are concurrently running two threads - t1 and t2

            we do not call the run() method directly, although we can
            - it will run t1 in the main thread (current thread) of the application
            - no new thread would be created
            - run() is the entrypoint of a thread execution

            we call the start() function to let Thread class to look
            for run() method and run it in a separate thread of the application
            start()
            - begins the execution of a new thread
            - initiates the execution of run() method in the new thread
            - takes care of the necessary preparation of the new thread to run
              such as setting up the thread stack
         */
        Thread t1 = new MyThread();
        t1.start();

        Thread t2 = new MyThread();
        t2.start();
    }
}
