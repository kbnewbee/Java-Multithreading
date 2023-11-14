package ch14;

import java.util.concurrent.Semaphore;

public class Connection {
    private static Connection connectionInstance = new Connection();

    /*
        To allow only 10 connections at a time, because there are only 10
        permits available

        Semaphores also provide an overloaded constructor with parameter fairness
        - if set true, it guarantees FIFO granting of permits under contention
          meaning which of thread reaches to acquire() method first will be granted
          the permit first when the permit becomes available
        - new Semaphore(10, true);
        - fairness set to false has performance benefits, as we do not want a thread to
          wait indefinitely in the background if something goes wrong
     */
    private Semaphore semaphore = new Semaphore(10);

    private int connections = 0;

    private Connection() {
    }

    public static Connection getConnectionInstance() {
        return connectionInstance;
    }

    public void connect() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
        }

        try {
            doConnect();
        } finally {
            semaphore.release();
        }

    }

    public void doConnect() {

        synchronized (this) {
            connections++;
            System.out.println("Current connections after increment= " + connections);

        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        synchronized (this) {
            connections--;
            System.out.println("Current connections after decrement= " + connections);
        }

    }


}
