package employees;

import accounts.Account;

public class ManagingDirector extends Employee {
    public static int appr=0;
    public ManagingDirector(String name) {
        super(name, EmployeeType.ManagingDirector);
    }

    // @Override
    // public void lookup(Account account) {
    //     System.out.println("Managing Director looking up account: " + account.getAccountHolderName());
    // }

    @Override
    public void approveLoan(Account account, double loanAmount) {
        account.loanapprovalvar=1;
        account.changeBalance(loanAmount);
        account.changeLoan(loanAmount);
        System.out.println("Managing Director approving loan for account: " + account.getAccountHolderName());

        
    }

    @Override
    public void changeInterestRate(Account accountType, double newInterestRate) {
        System.out.println("Managing Director changing interest rate for account type: " + accountType.getAccountType());
        accountType.setInterestRate(newInterestRate);
         
    
  
        
    }

    @Override
    public double seeInternalFund() {
        System.out.println("Managing Director checking internal fund: " );
        return -1;

    }
}
