package ch13;

public class App {
    public static void main(String[] args) {
        final Task task = new Task();

        Thread t1 = new Thread(() -> {
            task.firstTask();
        });

        Thread t2 = new Thread(() -> {
            task.secondTask();
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        }

        task.finish();

    }
}
