package ch12;

public class App {
    public static void main(String[] args) {
        final Processor processor = new Processor();
        Thread t1 = new Thread(() ->{
            try {
                processor.funcOne();
            } catch (InterruptedException e) {
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                processor.funcTwo();
            } catch (InterruptedException e) {
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        }

        processor.finish();
    }
}
