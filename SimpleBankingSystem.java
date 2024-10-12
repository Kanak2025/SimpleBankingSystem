import java.io.*;
import java.util.Scanner;

class BankAccount implements Serializable {
    private String accountHolder;
    private double balance;

    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited: $" + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrawn: $" + amount);
        } else {
            System.out.println("Insufficient funds or invalid withdrawal amount.");
        }
    }

    public void checkBalance() {
        System.out.println("Current balance: $" + balance);
    }

    public void saveAccountToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(accountHolder + ".dat"))) {
            oos.writeObject(this);
            System.out.println("Account information saved.");
        } catch (IOException e) {
            System.out.println("Error saving account information: " + e.getMessage());
        }
    }

    public static BankAccount loadAccountFromFile(String accountHolder) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(accountHolder + ".dat"))) {
            return (BankAccount) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading account: " + e.getMessage());
            return null;
        }
    }
}

public class SimpleBankingSystem {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            BankAccount account = null;

            while (true) {
                System.out.println("\nSimple Banking System Menu:");
                System.out.println("1. Create New Account");
                System.out.println("2. Load Existing Account");
                System.out.println("3. Deposit Money");
                System.out.println("4. Withdraw Money");
                System.out.println("5. Check Balance");
                System.out.println("6. Save Account");
                System.out.println("7. Exit");

                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter account holder name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter initial balance: ");
                        double initialBalance = scanner.nextDouble();
                        account = new BankAccount(name, initialBalance);
                        System.out.println("Account created successfully!");
                        break;
                    case 2:
                        System.out.print("Enter account holder name: ");
                        name = scanner.nextLine();
                        account = BankAccount.loadAccountFromFile(name);
                        if (account != null) {
                            System.out.println("Account loaded successfully.");
                        }
                        break;
                    case 3:
                        if (account != null) {
                            System.out.print("Enter amount to deposit: ");
                            double depositAmount = scanner.nextDouble();
                            account.deposit(depositAmount);
                        } else {
                            System.out.println("No account found. Please create or load an account first.");
                        }
                        break;
                    case 4:
                        if (account != null) {
                            System.out.print("Enter amount to withdraw: ");
                            double withdrawAmount = scanner.nextDouble();
                            account.withdraw(withdrawAmount);
                        } else {
                            System.out.println("No account found. Please create or load an account first.");
                        }
                        break;
                    case 5:
                        if (account != null) {
                            account.checkBalance();
                        } else {
                            System.out.println("No account found. Please create or load an account first.");
                        }
                        break;
                    case 6:
                        if (account != null) {
                            account.saveAccountToFile();
                        } else {
                            System.out.println("No account found. Please create or load an account first.");
                        }
                        break;
                    case 7:
                        System.out.println("Exiting system. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}
