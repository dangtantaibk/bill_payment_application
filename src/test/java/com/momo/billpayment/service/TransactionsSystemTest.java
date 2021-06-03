package com.momo.billpayment.service;

import com.momo.billpayment.domain.Bill;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.momo.billpayment.service.Billing;
import com.momo.billpayment.service.Billing;
import com.momo.billpayment.service.TransactionsSystem;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TransactionsSystemTest {

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    public void integrationTest() throws ParseException {
        TransactionsSystem transactionsSystem = TransactionsSystem.createInstance(100000);

        Bill billById1 = transactionsSystem.getBillById(1);
        Assert.assertEquals(billById1.toString(), new Bill(1, "ELECTRIC", 200000, formatter.parse("25/10/2020"), false, "EVN HCMC").toString());

        Bill billById2 = transactionsSystem.getBillById(2);
        Assert.assertEquals(billById2.toString(), new Bill(2, "WATER", 175000, formatter.parse("30/10/2020"), false, "SAVACO HCMC").toString());

        Bill billById3 = transactionsSystem.getBillById(3);
        Assert.assertEquals(billById3.toString(), new Bill(3, "INTERNET", 800000, formatter.parse("30/11/2020"), false, "VNPT").toString());

        long userAmount = transactionsSystem.getUserAmount();
        Assert.assertEquals(userAmount, 100000);
    }

    @Test
    public void payTest() throws ParseException {
        TransactionsSystem transactionsSystem = TransactionsSystem.createInstance(100000);
        transactionsSystem.pay(20000);
        long userAmount = transactionsSystem.getUserAmount();
        Assert.assertEquals(userAmount, 80000);
    }

    @Test
    public void updateBillingPaidTest() throws ParseException {
        TransactionsSystem transactionsSystem = TransactionsSystem.createInstance(100000);
        transactionsSystem.updateBillingPaid(1);
        
        Bill billById1 = transactionsSystem.getBillById(1);
        Assert.assertEquals(billById1.toString(), new Bill(1, "ELECTRIC", 200000, formatter.parse("25/10/2020"), true, "EVN HCMC").toString());
    }

    @Test
    public void insertTransactionsTest() throws ParseException {
        TransactionsSystem transactionsSystem = TransactionsSystem.createInstance(100000);
        Bill bill = new Bill(1, "ELECTRIC", 200000, formatter.parse("25/10/2020"), true, "EVN HCMC");
        transactionsSystem.insertTransactions(bill.getAmount(), bill.getDueDate(), 1, bill.getBillNo());
        
        List<TransactionsSystem.Transaction> listTransactions = transactionsSystem.getListTransactions();
        Assert.assertEquals(1, listTransactions.size());
    }

    @Test
    public void getListBillDueDateTest() {
        TransactionsSystem transactionsSystem = TransactionsSystem.createInstance(100000);
        List<Bill> listBillDueDate = transactionsSystem.getListBillDueDate();
        Assert.assertEquals(2, listBillDueDate.size());
    }
}
