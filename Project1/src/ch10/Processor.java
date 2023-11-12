package ch10;

import java.util.Scanner;

public class Processor {

    public void publish() throws InterruptedException {

        synchronized (this) {
            System.out.println("Starting to publish ...........");

            /*
                wait() method is a final method and belongs to Object class
                - it can only be called inside a synchronized block
                - the current thread releases the ownership of the lock or monitor
                - Causes the current thread to wait for the other thread to use
                  notify() or notifyAll() method or it gets timed out
             */
            wait();
            System.out.println("Wait is over, and received the lock again to continue to publish ......");
        }

    }

    public void consume() throws InterruptedException {
        Thread.sleep(2000);
        Scanner sc = new Scanner(System.in);

        synchronized (this) {
            System.out.println("Got the lock, starting to consume ..........");
            System.out.println("Press enter to continue consuming");
            sc.nextLine();
            /*
                notify() method is also a final method and belongs to the Object class
                - can only be called inside synchronized block
                - it will wake up the other thread who is waiting for
                  the lock acquisition
                - it does not hand over the lock, it just wakes up the other thread
            */
            notify();
            System.out.println("Enter pressed. wait for consume to finish .....");
            Thread.sleep(5000);
            System.out.println("Completed consuming ...........");
        }

    }
}
