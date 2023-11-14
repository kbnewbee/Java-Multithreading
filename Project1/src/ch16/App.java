package ch16;

import java.util.Random;

public class App {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting .........");

        Thread t1 = new Thread(() -> {
            Random random = new Random();

            for (int i = 0; i < 1E8; i++) {

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Someone interrupted ");
                    break;
                }
                Math.sin(random.nextDouble());
            }
        });

        t1.start();
        Thread.sleep(500);

        // t1.interrupt() does not kill or stop the thread
        t1.interrupt();

        t1.join();

        System.out.println("Finished .........");
    }
}
