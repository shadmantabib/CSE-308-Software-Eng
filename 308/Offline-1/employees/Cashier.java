package employees;


import accounts.Account;

public class Cashier extends Employee {
    public Cashier(String name) {
        super(name, EmployeeType.Cashier);
    }

    // @Override
    // public void lookup(Account account) {
    //     System.out.println("Cashier looking up account: " + account.getAccountHolderName());
    //     // Logic to lookup account details
    // }

    @Override
    public void approveLoan(Account account, double loanAmount) {
        System.out.println("Cashier cannot approve loans.");
       
    }

    @Override
    public void changeInterestRate(Account accountType, double newInterestRate) {
        System.out.println("Cashier cannot change interest rates.");
    
    }

    @Override
    public double seeInternalFund() {
        System.out.println("Cashier cannot see internal fund.");
        return -1;
     
    }
}
