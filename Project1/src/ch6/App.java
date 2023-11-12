package ch6;

public class App {

    public static void main(String[] args) {
        new Worker().work();
        new WorkerLock().work();
    }
}
