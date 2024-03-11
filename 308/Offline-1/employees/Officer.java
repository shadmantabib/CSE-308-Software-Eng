package employees;


import accounts.Account;

public class Officer extends Employee {
    public Officer(String name) {
        super(name, EmployeeType.Officer);
    }

    // @Override
    // public void lookup(Account account) {
    //     System.out.println("Officer looking up account: " + account.getAccountHolderName());
    //     // Logic to lookup account details
    // }

    @Override
    public void approveLoan(Account account, double loanAmount) {
        account.loanapprovalvar=1;
        account.changeBalance(loanAmount);
        account.changeLoan(loanAmount);
        // System.out.println(account.getLoan());
        // System.out.println("Officer approving loan for account: " + account.getAccountHolderName());
        // Logic to approve loan
    }

    @Override
    public void changeInterestRate(Account accountType, double newInterestRate) {
        System.out.println("Officer changing interest rate for account type: " + accountType.getAccountType());
        
    }

    @Override
    public double seeInternalFund() {
        System.out.println("Officers cannot see internal fund.");
        return -1;

    }
}
