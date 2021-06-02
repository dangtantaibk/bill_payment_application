package com.momo.billpayment.domain;

public interface Account {

    public long getCash();

    public int pay(long amount);

}
