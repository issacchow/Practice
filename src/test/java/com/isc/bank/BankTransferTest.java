package com.isc.bank;

import org.junit.Test;

public class BankTransferTest {




    /**
     * 死锁转账测试
     */
    @Test
    public void deadLockTransferTest() throws InterruptedException {

        final Account accountA = new Account();
        accountA.setAccountName("account-A");
        accountA.setBalance(1000);

        final Account accountB = new Account();
        accountB.setAccountName("account-B");
        accountB.setBalance(500);


        final DeadLockBank bank = new DeadLockBank();

        //模拟多线程转账
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                bank.transfer(accountA, accountB, 100);
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                bank.transfer(accountB, accountA, 100);
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }

}
