package ch3;

/*
    Method 2 of creating a thread : implementing the Runnable Interface

    This method of creating a thread can be improved in terms of readability
    by using Lambda function to provide implementation of the run() method directly
    in the constructor

*/

public class App {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("hello " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
    }

}
