package com.momo.billpayment.service;

import com.momo.billpayment.domain.Bill;
import java.util.HashMap;
import java.util.Map;

import com.momo.billpayment.service.exceptions.BillPaymentException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

class Billing {

    private static Billing billing;
    private Map<Integer, Bill> bills;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * VisibleForTesting(otherwise = PRIVATE)
     */
    protected Billing() {
        bills = new HashMap<Integer, Bill>();
        try {
            bills.put(1, new Bill(1, "ELECTRIC", 200000, formatter.parse("25/10/2020"), false, "EVN HCMC"));
            bills.put(2, new Bill(2, "WATER", 175000, formatter.parse("30/10/2020"), false, "SAVACO HCMC"));
            bills.put(3, new Bill(3, "INTERNET", 800000, formatter.parse("30/11/2020"), false, "VNPT"));
        } catch (ParseException ex) {
            new BillPaymentException("Sorry, data error");
        }
    }

    /**
     * Singleton Class => Returns a single instance of the class
     *
     * @return Billing instance
     */
    static Billing getInstance() {
        if (billing == null) {
            billing = new Billing();
        }
        return billing;
    }

    /**
     * List bill
     *
     * @return
     */
    List<Bill> getAll() {
        List<Bill> bs = new ArrayList<Bill>();
        for (Bill b : bills.values()) {
            bs.add(b);
        }
        return bs;
    }

    /**
     * Finds the bill due date
     *
     * @return list bills
     */
    List<Bill> findBillDueDate() {
        List<Bill> bs = new ArrayList<Bill>();
        for (Bill b : bills.values()) {
            if (b.getState() == false) {
                bs.add(b);
            }
        }
        return bs;
    }

    /**
     * Finds the bill by provider
     *
     * @return list bill
     */
    List<Bill> findBillByProvider(String s) {
        List<Bill> bs = new ArrayList<Bill>();
        for (Bill b : bills.values()) {
            if (b.getProvider().contains(s)) {
                bs.add(b);
            }
        }
        return bs;
    }
}
