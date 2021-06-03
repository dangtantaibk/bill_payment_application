package com.momo.billpayment.service;

import com.momo.billpayment.domain.Bill;
import com.momo.billpayment.service.TransactionsSystem.Transaction;
import java.util.List;

import com.momo.billpayment.service.exceptions.BillPaymentException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandExecutor {

    private static CommandExecutor commandExecutor;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private CommandExecutor() {

    }

    /**
     * Singleton Class => Returns a single instance of the class
     *
     * @return CommandExecutor instance
     */
    public static CommandExecutor getInstance() {
        if (commandExecutor == null) {
            commandExecutor = new CommandExecutor();
        }
        return commandExecutor;
    }

    private CommandName getCommandName(String commandString) {

        CommandName commandName = null;

        if (commandString == null) {
            System.out.println("Not a valid input");
        } else {
            String[] commandStringArray = commandString.split(" ");
            if ("".equals(commandStringArray[0])) {
                System.out.println("Not a valid input");
            } else {
                try {
                    commandName = CommandName.valueOf(commandStringArray[0]);
                } catch (Exception e) {
                    System.out.println("Unknown Command");
                }
            }
        }
        return commandName;

    }

    /**
     * the main function to execute the commands
     *
     * @param commandString
     * @return boolean if the execution is success or not
     */
    public boolean execute(String commandString) {

        CommandName commandName = getCommandName(commandString);

        if (commandName == null) {
            return false;
        }
        String[] commandStringArray = commandString.split(" ");
        Command command;

        switch (commandName) {
            case CASH_IN:
                command = new CashInCommand(commandStringArray);
                break;
            case LIST_BILL:
                command = new ListBillCommand(commandStringArray);
                break;
            case PAY:
                command = new PayCommand(commandStringArray);
                break;
            case DUE_DATE:
                command = new DueDateCommand(commandStringArray);
                break;
            case SCHEDULE:
                command = new ScheduleCommand(commandStringArray);
                break;
            case LIST_PAYMENT:
                command = new ListPaymentCommand(commandStringArray);
                break;
            case SEARCH_BILL_BY_PROVIDER:
                command = new SearchBillCommand(commandStringArray);
                break;
            default:
                System.out.println("Unknown Command");
                return false;
        }

        try {
            command.validate();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }

        String output = "";
        try {
            output = command.execute();
        } catch (BillPaymentException e) {
            System.out.print(e.getMessage());
        } catch (Exception e) {
            System.out.println("Unknown System Issue");
            e.printStackTrace();
            return false;
        }
        System.out.println(output);
        return true;
    }

    /**
     * All CommandNames
     *
     */
    private enum CommandName {
        CASH_IN, LIST_BILL, PAY, DUE_DATE, SCHEDULE,
        LIST_PAYMENT, SEARCH_BILL_BY_PROVIDER
    }

    /**
     * Command Interface which validates & executes the command
     *
     */
    private interface Command {

        public void validate();

        public String execute();
    }

    /**
     * Command Implementing Cash In
     *
     */
    private class CashInCommand implements Command {

        private String[] commandStringArray;

        CashInCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException("CASH_IN command should have exactly 1 argument");
            }
        }

        public String execute() {
            long amount = Long.parseLong(commandStringArray[1]);
            TransactionsSystem.createInstance(amount);
            return "Your available balance: " + commandStringArray[1] + " ";
        }
    }

    /**
     *
     */
    private class ListBillCommand implements Command {

        private String[] commandStringArray;

        ListBillCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 1) {
                throw new IllegalArgumentException("ListBill should have exactly 0 arguments");
            }
        }

        public String execute() {
            TransactionsSystem transactionsSystem = TransactionsSystem.getInstance();
            List<Bill> listBill = transactionsSystem.getListBill();
            StringBuilder s = new StringBuilder();

            s.append(String.format("%-20s%-20s%-20s%-20s%-20s%-20s\n", "Bill No.", "Type", "Amount", "Due Date", "State", "PROVIDER"));
            for (Bill b : listBill) {
                s.append(String.format("%-20s", b.getBillNo()));
                s.append(String.format("%-20s", b.getType()));
                s.append(String.format("%-20s", b.getAmount()));
                s.append(String.format("%-20s", formatter.format(b.getDueDate())));
                s.append(String.format("%-20s", b.getState() ? "PAID" : "NOT_PAID"));
                s.append(String.format("%-20s", b.getProvider()));
                s.append(String.format("\n"));
            }
            return s.toString();
        }
    }

    /**
     *
     */
    private class PayCommand implements Command {

        private String[] commandStringArray;

        PayCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length < 2) {
                throw new IllegalArgumentException("Pay command should have more than 1 or equal 1 argument");
            }
        }

        public String execute() {
            TransactionsSystem transactionsSystem = TransactionsSystem.getInstance();
            long totalAmount = 0;
            for (String idStr : commandStringArray) {
                if (idStr.equals("PAY")) {
                    continue;
                } 
                int billId = Integer.parseInt(idStr);
                Bill bill = transactionsSystem.getBillById(billId);
                if (bill == null) {
                    throw new  BillPaymentException("Sorry! Not found a bill with such id " + billId);
                }
                if (bill.getState()) {
                     throw new  BillPaymentException("Sorry! Bill was paid with such id " + billId);
                }
                totalAmount += bill.getAmount();
            }
            
            long userAmount = transactionsSystem.getUserAmount();
            if (userAmount < totalAmount) {
                throw new  BillPaymentException("Sorry! Not enough fund to proceed with payment. ");
            }
            StringBuilder s = new StringBuilder();
            
            for (String idStr : commandStringArray) {
                if (idStr.equals("PAY")) {
                    continue;
                } 
                int billId = Integer.parseInt(idStr);
                Bill bill = transactionsSystem.getBillById(billId);
                transactionsSystem.insertTransactions(bill.getAmount(), bill.getDueDate(), 1, bill.getBillNo());
                transactionsSystem.updateBillingPaid(bill.getBillNo());
                transactionsSystem.pay(bill.getAmount());
                s.append("Payment has been completed for Bill with id " + bill.getBillNo() + ".\n");
            }
            s.append("Your current balance is: " + transactionsSystem.getUserAmount());
            return s.toString();
        }
    }

    /**
     *
     */
    private class DueDateCommand implements Command {

        private String[] commandStringArray;

        DueDateCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 1) {
                throw new IllegalArgumentException("DueDate command should have no arguments");
            }
        }

        public String execute() {
            TransactionsSystem transactionsSystem = TransactionsSystem.getInstance();
            List<Bill> listBill = transactionsSystem.getListBillDueDate();
            StringBuilder s = new StringBuilder();

            s.append(String.format("%-20s%-20s%-20s%-20s%-20s%-20s\n", "Bill No.", "Type", "Amount", "Due Date", "State", "PROVIDER"));
            for (Bill b : listBill) {
                s.append(String.format("%-20s", b.getBillNo()));
                s.append(String.format("%-20s", b.getType()));
                s.append(String.format("%-20s", b.getAmount()));
                s.append(String.format("%-20s", formatter.format(b.getDueDate())));
                s.append(String.format("%-20s", b.getState() ? "PAID" : "NOT_PAID"));
                s.append(String.format("%-20s", b.getProvider()));
                s.append(String.format("\n"));
            }
            return s.toString();
        }
    }

    /**
     *
     */
    private class ScheduleCommand implements Command {

        private String[] commandStringArray;

        ScheduleCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 3) {
                throw new IllegalArgumentException(
                        "ScheduleCommand command should have exactly 2 argument");
            }
        }

        public String execute() {
            int id = Integer.parseInt(commandStringArray[1]);
            String dateStr = commandStringArray[2];
            try {
                formatter.parse(dateStr);
            } catch (ParseException ex) {
                throw new BillPaymentException(
                        "2nd argument is not correct date format");
            }

            return "Payment for bill id " + id + " is scheduled on " + dateStr + " ";
        }
    }

    /**
     *
     */
    private class ListPaymentCommand implements Command {

        private String[] commandStringArray;

        ListPaymentCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 1) {
                throw new IllegalArgumentException(
                        "ListPayment command should have exactly 0 argument");
            }
        }

        public String execute() {
            TransactionsSystem transactionsSystem = TransactionsSystem.getInstance();
            List<Transaction> listTrans = transactionsSystem.getListTransactions();
            StringBuilder s = new StringBuilder();
            s.append(String.format("%-20s%-20s%-20s%-20s%-20s\n", "No.", "Amount", "Payment Date", "State", "Bill Id"));
            for (Transaction b : listTrans) {
                s.append(String.format("%-20s", b.no));
                s.append(String.format("%-20s", b.amount));
                s.append(String.format("%-20s", formatter.format(b.paymentDate)));
                        // With state == 1 -> PROCESSED;        2 -> PENDING     
                s.append(String.format("%-20s", b.state == 1 ? "PROCESSED" : "PENDING"));
                s.append(String.format("%-20s", b.billNo));
                s.append(String.format("\n"));
            }
            return s.toString();
        }
    }

    /**
     *
     */
    private class SearchBillCommand implements Command {

        private String[] commandStringArray;

        SearchBillCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException(
                        "SearchBill Command should have exactly 1 argument");
            }
        }

        public String execute() {
            String provider = commandStringArray[1];
            TransactionsSystem transactionsSystem = TransactionsSystem.getInstance();
            List<Bill> listBill = transactionsSystem.getListByProvider(provider);
            StringBuilder s = new StringBuilder();

            s.append(String.format("%-20s%-20s%-20s%-20s%-20s%-20s\n", "Bill No.", "Type", "Amount", "Due Date", "State", "PROVIDER"));
            for (Bill b : listBill) {
                s.append(String.format("%-20s", b.getBillNo()));
                s.append(String.format("%-20s", b.getType()));
                s.append(String.format("%-20s", b.getAmount()));
                s.append(String.format("%-20s", formatter.format(b.getDueDate())));
                s.append(String.format("%-20s", b.getState() ? "PAID" : "NOT_PAID"));
                s.append(String.format("%-20s", b.getProvider()));
                s.append(String.format("\n"));
            }
            return s.toString();
        }
    }

}
