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
     * Parks a vehicle
     *
     * @return slotNumber => slot number at which the vehicle needs to be parked
     */
//    int issueParkingTicket(Vehicle vehicle) {
//        if (vehicle == null) {
//            throw new IllegalArgumentException("Vehicle cannot be null");
//        }
//        int assignedSlotNumber = billing.fillAvailableSlot();
//        Transaction ticket = new Transaction(assignedSlotNumber, vehicle);
//        transactions.put(assignedSlotNumber, ticket);
//        return assignedSlotNumber;
//    }
//
//    /**
//     * Exits a vehicle from the Bill payment
//     *
//     * @param registrationNumber
//     * @return slotNumber => the slot from the car has exited.
//     */
//    void exitVehicle(int slotNumber) {
//        if (transactions.containsKey(slotNumber)) {
//            billing.emptySlot(slotNumber);
//            transactions.remove(slotNumber);
//            return;
//        } else {
//            throw new BillPaymentException("No vehicle found at given slot. Incorrect input");
//        }
//    }
//
//    /**
//     * returns all the registration numbers of the vehicles with the given color
//     *
//     * @param color => Color of the Vehicle
//     * @return List of all the registration numbers of the vehicles with the
//     * given color
//     */
//    List<String> getRegistrationNumbersFromColor(String color) {
//        if (color == null) {
//            throw new IllegalArgumentException("color cannot be null");
//        }
//        List<String> registrationNumbers = new ArrayList<String>();
//        for (Transaction ticket : transactions.values()) {
//            if (color.equals(ticket.vehicle.getColor())) {
//                registrationNumbers.add(ticket.vehicle.getRegistrationNumber());
//            }
//        }
//        return registrationNumbers;
//    }
//
//    /**
//     * returns the slot number at which the Vehicle with given
//     * registrationNumber is parked
//     *
//     * @param registrationNumber => Registration Number of the Vehicle
//     * @return slot number at which the Vehicle with given registrationNumber is
//     * parked
//     */
//    int getSlotNumberFromRegistrationNumber(String registrationNumber) {
//        if (registrationNumber == null) {
//            throw new IllegalArgumentException("registrationNumber cannot be null");
//        }
//        for (Transaction ticket : transactions.values()) {
//            if (registrationNumber.equals(ticket.vehicle.getRegistrationNumber())) {
//                return ticket.slotNumber;
//            }
//        }
//
//        throw new BillPaymentException("Not found");
//    }
//
//    /**
//     * returns all the slot numbers of the vehicles with the given color
//     *
//     * @param color => Color of the Vehicle
//     * @return List of all the slot numbers of the vehicles with the given color
//     */
//    List<Integer> getSlotNumbersFromColor(String color) {
//        if (color == null) {
//            throw new IllegalArgumentException("color cannot be null");
//        }
//        List<Integer> registrationNumbers = new ArrayList<Integer>();
//        for (Transaction ticket : transactions.values()) {
//            if (color.equals(ticket.vehicle.getColor())) {
//                registrationNumbers.add(ticket.slotNumber);
//            }
//        }
//        return registrationNumbers;
//    }
//
//    /**
//     * returns the status of the ticketing system, a list of all the tickets
//     * converted to status objects
//     *
//     * @return List of StatusResponse => List of (slotNumber,
//     * registrationNumber, color)
//     */
//    List<StatusResponse> getStatus() {
//        List<StatusResponse> statusResponseList = new ArrayList<StatusResponse>();
//        for (Transaction ticket : transactions.values()) {
//            statusResponseList.add(new StatusResponse(ticket.slotNumber, ticket.vehicle.getRegistrationNumber(),
//                    ticket.vehicle.getColor()));
//        }
//        return statusResponseList;
//    }

    /**
     * Ticketing System issues a ticket => an object known only to Ticketing
     * System
     *
     */
    private class Transaction {

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
