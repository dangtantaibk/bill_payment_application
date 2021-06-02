package com.momo.billpayment.domain;

import java.util.Date;

public class Bill {

    private int billNo;
    private String type;
    private long amount;
    private Date dueDate;
    // boolean state to maintain => true=PAID, false=NOT_PAID
    private boolean state;
    private String provider;

    public Bill(int billNo, String type, long amount, Date dueDate, boolean state, String provider) {
        this.billNo = billNo;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = state;
        this.provider = provider;
    }

    public int getBillNo() {
        return billNo;
    }

    public void setBillNo(int billNo) {
        this.billNo = billNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return billNo + "           " + type + "      " + amount + "      " + dueDate + "         " + state + "       " + provider;
    }

}
