package com.isc.bank;

/**
 * 分段锁Bank
 * 提高效率，每次只锁定相关的资源,而不会全部锁上
 */
public class SegmentLockBank implements IBank {


    @Override
    public boolean transfer(Account from, Account to, long amount) {
        return false;
    }


    /**
     * 账号锁分配器
     * 不允许多个实例,只允许有一个分配器
     */
    private class AccountLockLocator{

        public AccountLockLocator signletion = new AccountLockLocator();



        private AccountLockLocator(){

        }



        public boolean acquireLock(Account... accounts){

            return true;

        }
    }


}
