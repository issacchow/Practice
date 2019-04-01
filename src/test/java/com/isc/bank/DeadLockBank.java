package com.isc.bank;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 出现死锁的Bank
 */
public class DeadLockBank implements IBank {

    //操作累计数
    private AtomicInteger operateNumber = new AtomicInteger(0);

    @Override
    public boolean transfer(Account from, Account to, long amount) {

        return transfer0(from, to, amount);
    }

    protected boolean transfer0(Account from, Account to, long amount) {
        int next = operateNumber.incrementAndGet();
        String transName = String.format("trans-%s", next);

        System.out.println(String.format("%s - 尝试锁定账号:%s", transName, from.getAccountName()));
        synchronized (from) {
            System.out.println(String.format("%s - 已锁定账号:%s", transName, from.getAccountName()));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            System.out.println(String.format("%s - 尝试锁定账号:%s", transName, to.getAccountName()));
            synchronized (to) {
                System.out.println(String.format("%s - 已锁定账号:%s", transName, from.getAccountName()));
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(String.format("%s 已转账金额 %s 到 %s",from.getAccountName(),amount,to.getAccountName()));
                return true;
            }

        }
    }
}
