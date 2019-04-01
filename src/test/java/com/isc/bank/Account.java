package com.isc.bank;

/**
 * 银行账号
 */
public class Account {

    //余额
    private long balance;

    //账号名称
    private String accountName;


    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
