package com.myname.week_13;

class CheckingAccount {
    private int balance;

    public CheckingAccount(int initialBalance)
    {
        balance = initialBalance;
    }

    synchronized public int withdraw(int amount)
    {
        if (amount <= balance && balance > 0)
        {
            try {
                Thread.sleep((int) (Math.random() * 200));
            }
            catch (InterruptedException ie) {
            }

            balance -= amount;
        }
        return balance;
    }
}

public class Main {
    public static void main(String[] args) {
        CheckingAccount account = new CheckingAccount(100);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                for (int i = 0; i < 10; i++) {
                    int withdrawalAmount = 10;
                    if (account.withdraw(withdrawalAmount) <= 0) {
                        System.out.println(name + " cannot withdraw $10, balance is zero.");
                        break;
                    } else {
                        System.out.println(name + " tries to withdraw $" + withdrawalAmount + ", balance: " +
                                account.withdraw(withdrawalAmount));
                    }
                }
            }
        };

        Thread thdHusband = new Thread(r);
        thdHusband.setName("Husband");

        Thread thdWife = new Thread(r);
        thdWife.setName("Wife");

        thdHusband.start();
        thdWife.start();
    }
}
