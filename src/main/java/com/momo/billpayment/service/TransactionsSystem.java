package com.momo.billpayment.service;

import com.momo.billpayment.domain.Bill;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.momo.billpayment.domain.User;
import com.momo.billpayment.service.exceptions.BillPaymentException;
import java.text.SimpleDateFormat;
import java.util.Date;

class TransactionsSystem {

    private static TransactionsSystem transactionsSystem;
    private Billing billing;
    private User user;
    private Map<Integer, Transaction> transactions;
    /**
     * VisibleForTesting(otherwise = PRIVATE)
     */
    TransactionsSystem(Billing parkingLot, User user) {
        this.billing = parkingLot;
        this.user = user;
        transactions = new HashMap<Integer, Transaction>();
    }

    /**
     * Singleton Class => Returns a single instance of the class
     *
     * ticketing system is managing
     * @return TicketingSystem instance
     */
    static TransactionsSystem createInstance(long amount) {
        if (transactionsSystem == null) {
            Billing parkingLot = Billing.getInstance();
            User user = new User(amount);
            transactionsSystem = new TransactionsSystem(parkingLot, user);
        }
        return transactionsSystem;
    }

    /**
     *
     * @return TicketingSystem instance
     */
    static TransactionsSystem getInstance() {
        if (transactionsSystem == null) {
            throw new IllegalStateException("Bill Payment is not initialized");
        }
        return transactionsSystem;
    }

    /**
     * List bill
     *
     * @return bills => List bill with state and amount
     */
    List<Bill> getListBill() {
        List<Bill> bills = billing.getAll();
        return bills;
    }
    
    /**
     * Get bill by Id
     *
     * @return bill => Get bill by id
     */
    Bill getBillById(int id) {
        return billing.getBillById(id);
    }
    
    /**
     * List bill due date
     *
     * @return bills => List bill with due date
     */
    List<Bill> getListBillDueDate() {
        List<Bill> bills = billing.findBillDueDate();
        return bills;
    }
    
    /**
     * Search bill provider by
     *
     * @return bills => List bill with due date
     */
    List<Bill> getListByProvider(String provider) {
        List<Bill> bills = billing.findBillByProvider(provider);
        return bills;
    }
    
    /**
     * Get user amount
     *
     * @return user's amount
     */
    long getUserAmount() {
        return user.getCash();
    }
    
    /**
     * Get user amount
     *
     * @return user's amount
     */
    void pay(long amount) {
        int pay = user.pay(amount);
        if (pay == -1) {
            throw new BillPaymentException("Amount is not enough");
        }
        return;
    }
    
    /**
     * Insert transaction
     *
     */
    void insertTransactions(long amount, Date paymentDate,int state, int billNo) {
        int no = transactions.size() + 1;
        
        Transaction transaction = new Transaction(no, amount, paymentDate, state, billNo);
        transactions.put(no, transaction);
        return;
    }
    
    /**
     * Update billing paid
     *
     */
    void updateBillingPaid(int id) {
        billing.updateBillingPaid(id);
        return;
    }


    /**
     * Get list transaction
     * registrationNumber is parked
     *
     * @return List Transaction
     */
    List<Transaction> getListTransactions() {
        List<Transaction> ret = new ArrayList<Transaction>();
        for (Transaction transaction : transactions.values()) {
            ret.add(transaction);
        }

        return ret;
    }

    /**
     * Transactions System issues a transaction => an object known only to Transaction
     * System
     *
     */
    public class Transaction {

        int no;
        long amount;
        Date paymentDate;
        // With state == 1 -> PROCESSED;        2 -> PENDING           
        int state;
        int billNo;

        public Transaction(int no, long amount, Date paymentDate, int state, int billNo) {
            this.no = no;
            this.amount = amount;
            this.paymentDate = paymentDate;
            this.state = state;
            this.billNo = billNo;
        }

    }
}
