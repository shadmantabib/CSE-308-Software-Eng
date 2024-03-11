package accounts;

public class CreateAccount {
    public Account createAccount(String name, String accountType, double initialDeposit)
    {
        
            
                if ("Savings".equals(accountType)) {
                    return new SavingsAccount(name, initialDeposit, accountType);
                } else if ("Student".equals(accountType)) {
                    return new StudentAccount(name, initialDeposit, accountType);
                } else if ("FixedDeposit".equals(accountType)) {
                    return new FixedDepositAccount(name, initialDeposit, accountType);
                } else {
                    System.out.println("Invalid Account Type");
                    return null;
                }
            
        
        



    } 
}
