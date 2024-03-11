package employees;
import accounts.Account;

public abstract class Employee implements IEmployeeOperations {
    protected String name;
    protected EmployeeType employeeType;

    public Employee(String name, EmployeeType employeeType) {
        this.name = name;   
        this.employeeType = employeeType;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public abstract void approveLoan(Account account, double loanAmount);

    @Override
    public void lookup(Account account) {
        System.out.println(account.getAccountHolderName() + " 's current balance " + account.getBalance());
    }

    @Override
    public abstract void changeInterestRate(Account accountType, double newInterestRate);

    @Override
    public abstract double seeInternalFund();
}
