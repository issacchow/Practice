package com.isc.bank;

public interface IBank {

    boolean transfer(Account from,Account to,long amount);

}
