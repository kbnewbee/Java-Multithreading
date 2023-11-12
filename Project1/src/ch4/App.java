package ch4;

import java.util.Scanner;

/*
    Importance of volatile keyword

    variable flag is a shared resource between the main thread and proc1 thread

    Some systems would cache the flag variable, so if the main thread calls the shutdown()
    method explicitly, there is a chance that the proc1 thread would still continue to run
    because it might be accessing the shared variable flag from its cache and hence result into
    inconsistency.

    In order to solve this issue, volatile keyword is introduced to store the shared variable
    in the RAM and all the thread who are accessing this variable would directly read from
    a single place to prevent inconsistent behavior.
 */

class Processor extends Thread{
    private volatile boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            System.out.println("Hello");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        flag = false;
    }
}

public class App {

    public static void main(String[] args) {
        Processor proc1 = new Processor();
        proc1.start();

        System.out.println("Press enter to stop execution .........");
        Scanner sc= new Scanner(System.in);
        sc.nextLine();

        // main thread is trying to call shutdown() method the proc1 thread
        // and changin the valur of the shared variable flag
        proc1.shutdown();

    }
}
