package com.isc.bank;

/**
 * 基于DeadLock修正过的bank
 * 增加了Synchrnoize,但锁的粒度太高了
 */
public class SynchronizeBank extends DeadLockBank {


    @Override
    public boolean transfer(Account from, Account to, long amount) {
        return transferSync(from,to,amount);
    }


    protected synchronized boolean transferSync(Account from, Account to, long amount) {
        return super.transfer0(from, to, amount);
    }
}
