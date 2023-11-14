package ch13;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    Deadlock situation arises when
    - first task has acquired lock1 and waiting to acquire lock2
    - second task has acquired lock2 and waiting to acquire lock1

    Solution 1
    - simply always maintain the order in which the locks are locked
    - but the issue is, if there are more than 2 locks, there is a chance that
      developer might mess up the exact order of all the locks
 */
public class Task {

    private BankAccount a1 = new BankAccount();
    private BankAccount a2 = new BankAccount();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    /*
        Solution 2
        - use tryLock() method for both the locks until both return true
        - tryLock() will return true if the lock is free to acquire
        - so if a thread has both the locks acquired, then only, it will
          come out of the loop - this is the idea

     */
    public void acquiredLocks(Lock l1, Lock l2) {
        while (true) {
            // Attempt to acquire locks
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;

            try {
                gotFirstLock = l1.tryLock();
                gotSecondLock = l2.tryLock();
            } finally {
                if (gotFirstLock && gotSecondLock) return;
                if (gotFirstLock) l1.unlock();
                if (gotSecondLock) l2.unlock();

            }


        }
    }

    public void firstTask() {
        Random random = new Random();


        for (int i = 0; i < 10000; i++) {
            // lock1.lock();
            // lock2.lock();

            acquiredLocks(lock1, lock2);
            try {
                BankAccount.transfer(a1, a2, random.nextInt(100));

            } finally {
                lock1.unlock();
                lock2.unlock();

            }
        }

    }

    public void secondTask() {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            // lock1.lock();
            // lock2.lock();

            acquiredLocks(lock2, lock1);
            try {
                BankAccount.transfer(a2, a1, random.nextInt(100));

            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finish() {
        System.out.println("Account 1 balance = " + a1.getBalance());
        System.out.println("Account 2 balance = " + a2.getBalance());
        System.out.println("Total balance = " + (a1.getBalance() + a2.getBalance()));
    }
}
