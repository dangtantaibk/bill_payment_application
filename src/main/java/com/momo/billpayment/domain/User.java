package com.momo.billpayment.domain;

public class User implements Account {

    private long cash;

    public User(long cash) {
        if (cash <= 0) {
            throw new IllegalArgumentException("Cash invalid");
        }
        this.cash = cash;
    }

    public long getCash() {
        return this.cash;
    }
    
    public void setCash(long cash) {
        this.cash = cash;
    }

    public int pay(long amount) {
        if (amount > cash) {
            return -1;
        }
        this.cash = cash - amount;
        return 0;
    }

}
