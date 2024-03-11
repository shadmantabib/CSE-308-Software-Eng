
import java.util.HashMap;
import java.util.Map;

import accounts.Account;
import accounts.CreateAccount;
import employees.Cashier;
import employees.Employee;
import employees.EmployeeType;
import employees.ManagingDirector;
import employees.Officer;

public class Bank {
    // Singleton Class,it can not be initiated more than once
    private static Bank instance;
    public double internalFund;
    private static int yearCount = 0;
    public EmployeeType typeemp;
    private CreateAccount createaccount = new CreateAccount();
    private Map<String, Account> accountMap;
    private Map<String, Employee> empMap;
    private Map<Account, Double> loanMap;

    private Bank() {
        // Private constructor to prevent instantiation
        // Singletone class
        empMap = new HashMap<>();
        accountMap = new HashMap<>();
        loanMap = new HashMap<Account, Double>();
        internalFund = 1000000;
    }

    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    public double infoInternalFund() {
        return internalFund;
    }

    public void createAccount(String name, String accountType, double initialAmount) {

        if (accountMap.containsKey(name)) {
            System.out.println("Account already exists");
        }
        Account account = createaccount.createAccount(name, accountType, initialAmount);
        internalFund = internalFund + initialAmount;
        if (account != null) {
            accountMap.put(name, account);
        }
    }

    public void initializeEmployees() {
        // Create Managing Director
        ManagingDirector managingDirector = new ManagingDirector("MD");
        empMap.put("MD", managingDirector);
        // Create Officers
        Officer officer1 = new Officer("O1");
        Officer officer2 = new Officer("O2");
        empMap.put("O1", officer1);
        empMap.put("O2", officer2);
        // Create Cashiers
        Cashier cashier1 = new Cashier("C1");
        Cashier cashier2 = new Cashier("C2");
        Cashier cashier3 = new Cashier("C3");
        Cashier cashier4 = new Cashier("C4");
        Cashier cashier5 = new Cashier("C5");
        empMap.put("C1", cashier1);
        empMap.put("C2", cashier2);
        empMap.put("C3", cashier3);
        empMap.put("C4", cashier4);
        empMap.put("C5", cashier5);

    }

    public void deposit(String name, double amount) {
        // find the account
        Account account = accountMap.get(name);
        if (account != null) {
            account.deposit(amount);
            internalFund += amount;

        } else {
            System.out.println("Account not found");
        }
    }

    public static double getInternalFund(Bank b) {
        return b.internalFund;
    }

    public int getYearCount() {
        return yearCount;
    }

    public void incrementYear(String activeClient) {
        Account account = accountMap.get(activeClient);
        account.processAnnualChanges();
        yearCount++;
        System.out.println(yearCount + " year passed");
    }

    public double getBalance(String activeClient) {
        Account account = accountMap.get(activeClient);
        return account.getBalance();
    }

    public void withdraw(String activeClient, double amount) {
        Account account = accountMap.get(activeClient);
        if (account.getAccountType() == "FixedDeposit" && yearCount == 0) {
            System.out.println("Cannot withdraw ");
        } else {
            int p = account.withdraw(amount);
            if (p == 1)
                internalFund -= amount;

        }
    }

    public void queryDeposit(String activeClient) {
        Account account = accountMap.get(activeClient);
        if (account.getLoan() != 0) {
            System.out.println("Current balance :" + account.getBalance() + " loan " + account.getLoan());
        } else {
            System.out.println("Current balance :" + account.getBalance());
        }
    }

    public void requestLoan(String activeClient, double amount) {
        Account account = accountMap.get(activeClient);

        int f = account.requestLoan(amount);
        if (f == 1) {
            loanMap.put(account, amount);
            account.loanapprovalvar = 0;
            
        }
        
    }

    public void open(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Invalid name provided");
            return;
        }

        if (accountMap.containsKey(name)) {
            handleUserLogin(name);
        } else if (empMap.containsKey(name)) {
            handleEmployeeLogin(name);
        } else {
            System.out.println("Account not found");
        }
    }

    private void handleUserLogin(String userName) {
        System.out.println("Welcome back, " + userName);
    }

    private void handleEmployeeLogin(String employeeName) {
        Employee employee = empMap.get(employeeName);
        EmployeeType type = employee.getEmployeeType();
        typeemp = type;
        if (type == EmployeeType.ManagingDirector || type == EmployeeType.Officer) {
            System.out.println(employeeName + " active, there are loan approvals pending");
        }
    }

    public void approvePendingLoan(String employeeName) {
        // Validate input and existence of employee
        if (employeeName == null || employeeName.isEmpty()) {
            System.out.println("Invalid employee name provided");
            return;
        }

        if (!empMap.containsKey(employeeName)) {
            System.out.println("Employee not found");
            return;
        }

        // Validate that there are loans to approve
        if (loanMap.isEmpty()) {
            System.out.println("No pending loans to approve");
            return;
        }

        // Retrieve the employee
        Employee employee = empMap.get(employeeName);
        // System.out.println(employee.getname());
        // System.out.println(loanMap);
        // Approve each pending loan
        if (empMap.containsKey(employeeName)) {

            if (!loanMap.isEmpty()) {
                for (Account account : loanMap.keySet()) {
                    double loanAmount = loanMap.get(account); // Get the loan amount for the current account
                    employee.approveLoan(account, loanAmount);
                    internalFund -= loanAmount;
                    System.out.println("Loan for " + account.getAccountHolderName() + " approved");
                }
            }
        }
    }

    public void changeInterestRate(String userName, String accountType, double interestRate) {
        int flag = 0;
        if (empMap.containsKey(userName)) {

            Employee employee = empMap.get(userName);
            // System.out.println(employee.getEmployeeType());
            // Iterate through the account values in the map
            for (Account account : accountMap.values()) {
                // Check if the account type matches
                if (account.getAccountType().equals(accountType)
                        && employee.getEmployeeType() == EmployeeType.ManagingDirector) {
                    employee.changeInterestRate(account, interestRate / 100.0);
                    System.out.println("Changed interest rate for " + accountType + " to " + interestRate + "%");
                    flag = 1;
                }
            }
        }
        if (flag != 1) {
            System.out.println("You dont have permission for this operation");
        }
    }

    public void lookup(String employeeId, String accountId) {
        // Validate input parameters
        if (employeeId == null || employeeId.isEmpty() || accountId == null || accountId.isEmpty()) {
            System.out.println("Invalid input");
            return;
        }

        // Check if the employee exists
        if (!empMap.containsKey(employeeId)) {
            System.out.println("Employee not found");
            return;
        }

        // Retrieve the employee
        Employee employee = empMap.get(employeeId);

        // Check if the account exists
        if (!accountMap.containsKey(accountId)) {
            System.out.println("Bank Account not found");
            return;
        }

        // Perform the lookup operation
        employee.lookup(accountMap.get(accountId));
    }

    public void InternalFund(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Employee name cannot be empty.");
            return;
        }

        Employee employee = empMap.get(name);
        if (employee != null && employee.getEmployeeType() == EmployeeType.ManagingDirector) {
            double fundAmount = internalFund;
            System.out.println("Internal fund: " + fundAmount);
        } else {
            System.out.println("You don’t have permission for this operation.");
        }
    }

    public void printEmployeesOrder() {
        StringBuilder output = new StringBuilder("Bank Created; ");

        for (String empId : empMap.keySet()) {
            output.append(empId).append(", ");
        }

        // Remove the last comma and space
        if (output.length() > 2) {
            output.setLength(output.length() - 2);
        }

        output.append(" created");

        System.out.println(output);
    }

}
