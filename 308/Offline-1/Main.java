import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Bank bank = Bank.getInstance();
        bank.initializeEmployees();
        bank.printEmployeesOrder();
        String active = "";

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String[] input = scanner.nextLine().split(" ");
            String command = input[0];
            try {
                if (command.equals("Exit")) {
                    System.out.println("Exiting the banking system. Goodbye!");
                    break;
                } 
                else if (command.equals("Create")) {
                    
                    bank.createAccount(input[1], input[2], Double.parseDouble(input[3]));
                   active = input[1];
                    
                    System.out.println(input[2] + " account for " + input[1] + " Created; initial balance " + input[3] + "$");
                } else if (command.equals("Deposit")) {
                    bank.deposit(active, Double.parseDouble(input[1]));
                    System.out.println(input[1] + "$ deposited; current balance " + bank.getBalance(active) + "$");
                } else if (command.equals("Withdraw")) {
                    // Remove commas from the input amount string and parse it to double
                    double amount = Double.parseDouble(input[1].replace(",", ""));
                    
                    bank.withdraw(active, amount);
                    
                }
                 else if (command.equals("Query")) {
                    
                    bank.queryDeposit(active);
                    
                } else if (command.equals("Request")) {
                    bank.requestLoan(active, Double.parseDouble(input[1]));
                    
                } else if (command.equals("Close")) {
                    System.out.println("Transaction Closed for " +active);
                     
                } else if (command.equals("Open")) {
                    bank.open(input[1]);
                   active = input[1];
                    
                } else if (command.equals("Approve")) {
                    
                    bank.approvePendingLoan(active);
                    
                } else if (command.equals("Change")) {
                    bank.changeInterestRate(active, input[1], Double.parseDouble(input[2]));
                    
                } else if (command.equals("Lookup")) {
                    bank.lookup(active, input[1]);
                } else if (command.equals("See")) {
                    bank.InternalFund(active);
                } else if (command.equals("INC")) {
                    
                    bank.incrementYear(active);
                    
                } else {
                    System.out.println("Invalid command! Please try again.");
                }
            } 
             catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("It seems you didn't enter the correct number of arguments for the command.");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}