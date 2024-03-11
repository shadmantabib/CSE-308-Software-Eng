package accounts;


public abstract class Account {
    protected String accountHolderName;
    protected double balance;
    protected String acctype;

    protected double loan;
    public static int maturityReached = 0;
    public int loanapprovalvar;
    protected double interestRate;

    public Account(String accountHolderName, double initialDeposit, String acctype) {
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be less than 0");
        }

        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.acctype = acctype;
        this.loan = 0;
        this.loanapprovalvar = 0;
    }

    public String getAccountType() {
        return acctype;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getLoan() {
        return this.loan;
    }

    public double getBalance() {
        return balance;
    }

    public void changeBalance(double sub) {
        this.balance += sub;
    }

    public void changeLoan(double sub) {
        this.loan += sub;
    }

    abstract public void deposit(double amount);

    public abstract int withdraw(double amount);

    public void queryDeposit() {

        System.out.println("Current balance " + balance + "$");
    }

    public void processAnnualChanges() {
        // Apply interest rates and deduct service charge after one year

        this.applyInterestRate();
        this.deductServiceCharge();

        double inter = loan * 0.1;

        balance -= inter;

    }

    public abstract void setInterestRate(double newinterestRate);

    protected abstract void applyInterestRate();

    protected abstract void deductServiceCharge();

    public abstract int requestLoan(double amount);
}

class SavingsAccount extends Account {

    public SavingsAccount(String accountHolderName, double initialDeposit, String acctype) {

        super(accountHolderName, initialDeposit, acctype);
        interestRate = 0.1;
    }

    @Override
    public void setInterestRate(double newInterestRate) {
        this.interestRate = newInterestRate;
    }

    @Override
    public void deposit(double amount) {
        if (amount < 0) {
            System.out.println("Invalid Amount! Amount should be greater than 0");
        }
        this.balance += amount;
    }

    @Override
    public int requestLoan(double amount) {
        int f = 0; // Initialize f to 0 by default

        if (acctype.equals("Savings")) {
            if (amount <= 10000) {
                System.out.println("Loan request successful, sent for approval");
                f = 1; // Set f to 1 when the loan request is successful
            } else {

                System.out.println("Loan request exceeds limit for Savings account");
            }
        } else {
            System.out.println("Invalid transaction for account type " + acctype);
        }

        return f; // Ensure f is returned in all cases
    }

    @Override
    public int withdraw(double amount) {

        if (acctype.equals("Savings") && balance - amount >= 1000) {
            balance -= amount;

            System.out.println(amount + "$ withdrawn; current balance " + getBalance() + "$");
            return 1;
        } else {
            System.out.println("Invalid transaction; current balance " + getBalance() + "$");
            return 0;
        }
    }

    @Override
    protected void applyInterestRate() {

        balance += balance * interestRate;
    }

    @Override
    protected void deductServiceCharge() {

        balance -= 500;
    }
}

class StudentAccount extends Account {

    public StudentAccount(String accountHolderName, double initialDeposit, String acctype) {
        super(accountHolderName, initialDeposit, acctype);
        interestRate = .05;

    }

    @Override
    public void setInterestRate(double newInterestRate) {
        this.interestRate = newInterestRate;
    }

    @Override
    public void deposit(double amount) {
        if (amount < 0) {
            System.out.println("Invalid Amount! Amount should be greater than 0");
        }
        this.balance += amount;
    }

    public int requestLoan(double amount) {
        int f;

        if (acctype.equals("Student") && amount <= 1000) {
            System.out.println("Loan request successful, sent for approval");
            f = 1; // Set f to 1 when the loan request is successful
        } else {
            System.out.println("Invalid transaction for account type " + acctype);
            f = 0; // Set f to 0 in case of an invalid transaction
        }

        return f; // Return the int value
    }

    @Override
    public int withdraw(double amount) {

        if (acctype.equals("Student") && amount <= 10000) {
            this.balance -= amount;
            System.out.println(amount + "$ withdrawn; current balance " + getBalance() + "$");
            return 1;
        } else {
            System.out.println("Invalid transaction; current balance " + getBalance() + "$");
            return 0;
        }
    }

    @Override
    public void applyInterestRate() {

        balance += balance * interestRate;
    }

    @Override
    protected void deductServiceCharge() {

        balance -= 0;
    }
}

class FixedDepositAccount extends Account {

    public FixedDepositAccount(String accountHolderName, double initialDeposit, String acctype) {

        super(accountHolderName, initialDeposit, acctype);

        interestRate = .15;

    }

    @Override
    public void setInterestRate(double newInterestRate) {
        this.interestRate = newInterestRate;
    }

    public int requestLoan(double amount) {
        int f;

        if (amount <= 100000) {
            System.out.println("Loan request successful, sent for approval");
            f = 1; // Set f to 1 when the loan request is successful
        } else {
            System.out.println("Invalid transaction for account type " + acctype);
            f = 0; // Set f to 0 in case of an invalid transaction
        }

        return f; // Return the int value
    }

    @Override
    public int withdraw(double amount) {

        if (acctype.equals("Fixed Deposit") && balance - amount >= 0) {
            balance -= amount;
            System.out.println(amount + "$ withdrawn; current balance " + getBalance() + "$");
            return 1;
        } else {
            System.out.println("Invalid transaction; current balance " + getBalance() + "$");
            return 0;
        }
    }

    @Override
    public void deposit(double amount) {
        if (amount < 50000) {
            System.out.println("Invalid Amount! Minimum amount should be at least 50,000$");
        }
        this.balance += amount;
    }

    @Override
    protected void applyInterestRate() {

        balance += balance * 0.15;
    }

    @Override
    protected void deductServiceCharge() {

        balance -= 500;
    }
}
