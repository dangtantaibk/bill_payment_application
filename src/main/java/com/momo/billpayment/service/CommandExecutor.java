package com.momo.billpayment.service;

import com.momo.billpayment.domain.Bill;
import java.util.List;

import com.momo.billpayment.service.exceptions.BillPaymentException;
import java.text.SimpleDateFormat;

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
                command = new LeaveCommand(commandStringArray);
                break;
            case DUE_DATE:
                command = new StatusCommand(commandStringArray);
                break;
            case SCHEDULE:
                command = new RegistrationNumbersForColorCommand(commandStringArray);
                break;
            case LIST_PAYMENT:
                command = new SlotNumbersForColorCommand(commandStringArray);
                break;
            case SEARCH_BILL_BY_PROVIDER:
                command = new SlotNumberCommand(commandStringArray);
                break;
            default:
                System.out.println("Unknown Command");
                return false;
        }

        try {
            command.validate();
        } catch (IllegalArgumentException e) {
            System.out.println("Please provide a valid argument");
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
     * Command Implementing create_parking_lot
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
     * holds the responsibility of implementing park command
     *
     */
    private class ListBillCommand implements Command {

        private String[] commandStringArray;

        ListBillCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 1) {
                throw new IllegalArgumentException("park command should have exactly 2 arguments");
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
                s.append(String.format("%-20s", b.getState() ? "PAID": "NOT_PAID"));
                s.append(String.format("%-20s", b.getProvider()));
                s.append(String.format("\n"));
            }
            return s.toString();
        }
    }

    /**
     * holds the responsibility of implementing leave command
     *
     */
    private class LeaveCommand implements Command {

        private String[] commandStringArray;

        LeaveCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException("leave command should have exactly 1 argument");
            }
        }

        public String execute() {
//            TransactionsSystem ticketingSystem = TransactionsSystem.getInstance();
//            ticketingSystem.exitVehicle(Integer.parseInt(commandStringArray[1]));
//            return "Slot number " + commandStringArray[1] + " is free";
            return "";
        }
    }

    /**
     * holds the responsibility of implementing status command
     *
     */
    private class StatusCommand implements Command {

        private String[] commandStringArray;

        StatusCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 1) {
                throw new IllegalArgumentException("status command should have no arguments");
            }
        }

        public String execute() {
//            TransactionsSystem ticketingSystem = TransactionsSystem.getInstance();
//            List<StatusResponse> statusResponseList = ticketingSystem.getStatus();
//
//            StringBuilder outputStringBuilder = new StringBuilder("Slot No.    Registration No    Colour");
//            for (StatusResponse statusResponse : statusResponseList) {
//                outputStringBuilder.append("\n").append(statusResponse);
//            }
//            return outputStringBuilder.toString();
            return "";
        }
    }

    /**
     * holds the responsibility of implementing
     * registration_numbers_for_cars_with_colour command
     *
     */
    private class RegistrationNumbersForColorCommand implements Command {

        private String[] commandStringArray;

        RegistrationNumbersForColorCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException(
                        "registration_numbers_for_cars_with_colour command should have exactly 1 argument");
            }
        }

        public String execute() {
//            TransactionsSystem ticketingSystem = TransactionsSystem.getInstance();
//            List<String> registrationNumbersList = ticketingSystem
//                    .getRegistrationNumbersFromColor(commandStringArray[1]);
//            StringBuilder outputStringBuilder = new StringBuilder();
//            for (String registrationNumber : registrationNumbersList) {
//                if (outputStringBuilder.length() > 0) {
//                    outputStringBuilder.append(", ");
//                }
//                outputStringBuilder.append(registrationNumber);
//            }
//            return outputStringBuilder.toString();
            return "";
        }
    }

    /**
     * holds the responsibility of implementing
     * slot_numbers_for_cars_with_colour command
     *
     */
    private class SlotNumbersForColorCommand implements Command {

        private String[] commandStringArray;

        SlotNumbersForColorCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException(
                        "slot_numbers_for_cars_with_colour command should have exactly 1 argument");
            }
        }

        public String execute() {
//            TransactionsSystem ticketingSystem = TransactionsSystem.getInstance();
//            List<Integer> slotNumbersList = ticketingSystem.getSlotNumbersFromColor(commandStringArray[1]);
//            StringBuilder outputStringBuilder = new StringBuilder();
//            for (int slotNumber : slotNumbersList) {
//                if (outputStringBuilder.length() > 0) {
//                    outputStringBuilder.append(", ");
//                }
//                outputStringBuilder.append(slotNumber);
//            }
//            return outputStringBuilder.toString();
            return "";
        }
    }

    /**
     * holds the responsibility of implementing
     * slot_number_for_registration_number command
     *
     */
    private class SlotNumberCommand implements Command {

        private String[] commandStringArray;

        SlotNumberCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException(
                        "slot_number_for_registration_number command should have exactly 1 argument");
            }
        }

        public String execute() {
//            TransactionsSystem ticketingSystem = TransactionsSystem.getInstance();
//            int slotNumber = ticketingSystem.getSlotNumberFromRegistrationNumber(commandStringArray[1]);
//            return "" + slotNumber;
            return "";
        }
    }

}
