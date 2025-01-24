package project_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class Account {
    private int accountId;
    private String accountHolder;
    private double balance;
    private static final String CUSTOMER_CALL_NUMBER = "11100011";

    public Account(int accountId, String accountHolder, double balance) {
        this.accountId = accountId;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("\u20b9" + amount + " deposited successfully. New Balance: \u20b9" + balance);
        } else {
            System.out.println("Invalid amount! Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("\u20b9" + amount + " withdrawn successfully. Remaining Balance: \u20b9" + balance);
        } else if (amount > balance) {
            System.out.println("Insufficient balance! Cannot withdraw \u20b9" + amount);
        } else {
            System.out.println("Invalid amount! Withdrawal amount must be positive.");
        }
    }

    public void transfer(Account toAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            toAccount.balance += amount;
            System.out.println("\u20b9" + amount + " transferred successfully to Account ID: " + toAccount.getAccountId());
        } else if (amount > balance) {
            System.out.println("Insufficient balance! Cannot transfer \u20b9" + amount);
        } else {
            System.out.println("Invalid amount! Transfer amount must be positive.");
        }
    }

    @Override
    public String toString() {
        return "Account ID: " + accountId + ", Holder: " + accountHolder + ", Balance: \u20b9" + balance + ", Customer Call Number: " + CUSTOMER_CALL_NUMBER;
    }
}

public class project_1 {
    private static HashMap<Integer, Account> accounts = new HashMap<>();
    private static List<String> transactionHistory = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        while (true) {
            System.out.println("\n--- Banking Application ---");
            System.out.println("1. Add Account");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Transfer Money");
            System.out.println("6. History");
            System.out.println("7. Delete Account");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addAccount();
                        break;
                    case 2:
                        checkBalance();
                        break;
                    case 3:
                        depositMoney();
                        break;
                    case 4:
                        withdrawMoney();
                        break;
                    case 5:
                        transferMoney();
                        break;
                    case 6:
                        showHistory();
                        break;
                    case 7:
                        deleteAccount();
                        break;
                    case 8:
                    	System.out.println("FOR FURTHER ENQUIRY CONTACT OUR CUSTOMER CARE TOLL FREE NUMBER \n 1800 1200 1300 or 1800 1200 1400");
                        System.out.println("Thank you for using the banking application. Please come again.");
                        return;
                    default:
                        System.out.println("Invalid choice! Please choose a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    private static void addAccount() {
        try {
            System.out.print("Enter New Account ID: ");
            int accountId = Integer.parseInt(scanner.nextLine());
            if (accounts.containsKey(accountId)) {
                System.out.println("Account ID already exists! Please use a different ID.");
                return;
            }
            System.out.print("Enter Account Holder Name: ");
            String accountHolder = scanner.nextLine();
            System.out.print("Enter Initial Balance: \u20b9");
            double balance = Double.parseDouble(scanner.nextLine());
            accounts.put(accountId, new Account(accountId, accountHolder, balance));
            System.out.println("Account added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numeric values where required.");
        }
    }

    private static void deleteAccount() {
        try {
            System.out.print("Enter Account ID to delete: ");
            int accountId = Integer.parseInt(scanner.nextLine());
            if (accounts.containsKey(accountId)) {
                accounts.remove(accountId);
                System.out.println("Account with ID " + accountId + " deleted successfully.");
            } else {
                System.out.println("Account with ID " + accountId + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid account ID.");
        }
    }

    private static void checkBalance() {
        try {
            System.out.print("Enter Account ID: ");
            int accountId = Integer.parseInt(scanner.nextLine());
            Account account = getAccountById(accountId);
            if (account != null) {
                System.out.println(account);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid account ID.");
        }
    }

    private static void depositMoney() {
        try {
            System.out.print("Enter Account ID: ");
            int accountId = Integer.parseInt(scanner.nextLine().trim());
            Account account = getAccountById(accountId);
            if (account != null) {
                System.out.print("Enter amount to deposit: \u20b9");
                double amount = Double.parseDouble(scanner.nextLine());
                account.deposit(amount);
                transactionHistory.add("Deposit: \u20b9" + amount + " into Account ID: " + accountId);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numeric values for amount.");
        }
    }

    private static void withdrawMoney() {
        try {
            System.out.print("Enter Account ID: ");
            int accountId = Integer.parseInt(scanner.nextLine().trim());
            Account account = getAccountById(accountId);
            if (account != null) {
                System.out.print("Enter amount to withdraw: \u20b9");
                double amount = Double.parseDouble(scanner.nextLine());
                if (account.getBalance() >= amount) {
                    transactionHistory.add("Withdrawal: \u20b9" + amount + " from Account ID: " + accountId);
                } else {
                    transactionHistory.add("Transaction Failed: \u20b9" + amount + " from Account ID: " + accountId + " Insufficient Balance");
                }
                account.withdraw(amount);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numeric values for amount.");
        }
    }

    private static void transferMoney() {
        try {
            System.out.print("Enter your Account ID: ");
            int fromAccountId = Integer.parseInt(scanner.nextLine());
            Account fromAccount = getAccountById(fromAccountId);
            if (fromAccount != null) {
                System.out.print("Enter the Receiver's Account ID: ");
                int toAccountId = Integer.parseInt(scanner.nextLine());
                Account toAccount = getAccountById(toAccountId);
                if (toAccount != null) {
                    System.out.print("Enter amount to transfer: \u20b9");
                    double amount = Double.parseDouble(scanner.nextLine());
                    if (fromAccount.getBalance() >= amount) {
                        transactionHistory.add("Transfer: \u20b9" + amount + " from Account ID: " + fromAccountId + " to Account ID: " + toAccountId);
                    } else {
                        transactionHistory.add("Transaction Failed: \u20b9" + amount + " from Account ID: " + fromAccountId + " Insufficient Balance");
                    }
                    fromAccount.transfer(toAccount, amount);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numeric values for account IDs or amount.");
        }
    }

    private static void showHistory() {
        try {
            if (transactionHistory.isEmpty()) {
                System.out.println("No transaction history available!");
            } else {
                System.out.println("\n--- Transaction History ---");
                for (String transaction : transactionHistory) {
                    System.out.println(transaction);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numeric values for account IDs or amount.");
        }
    }

    private static Account getAccountById(int accountId) {
        if (accounts.containsKey(accountId)) {
        	return accounts.get(accountId);
        } else {
        	System.out.println("Account ID not found");
        	return null;
        }
    }
}
