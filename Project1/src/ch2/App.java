package ch2;

/*
    Method 2 of creating a thread : implementing the Runnable Interface

    This method is better than method 1 because
    - Java does not allow multiple inheritance, so a class can
      only extend on parent class, hence limiting the future possibility
      of inheritance
    - However Java allows classes to implement multiple interfaces,
      hence giving the flexibility to add inheritance for future
*/

class MyThread2 implements Runnable {

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
        Thread t1 = new Thread(new MyThread2());
        Thread t2 = new Thread(new MyThread2());

        t1.start();
        t2.start();

    }
}
