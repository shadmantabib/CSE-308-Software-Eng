package employees;
import accounts.Account;

public interface IEmployeeOperations {
    void approveLoan(Account account, double loanAmount);
    void lookup(Account account);
    void changeInterestRate(Account accountType, double newInterestRate);
    double seeInternalFund();
}