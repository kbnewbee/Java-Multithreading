package ch13;

public class BankAccount {

    private int balance = 1000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(BankAccount a1, BankAccount a2, int amount) {
        a1.withdraw(amount);
        a2.deposit(amount);
    }
}
