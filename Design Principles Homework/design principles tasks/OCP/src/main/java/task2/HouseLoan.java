package task2;

public class HouseLoan extends LoanCalculator {

    private static final int DEFAULT_LOAN = 100_000;
    private static final int AGE = 30;
    private static final int MULTIPLIER = 2;

    public HouseLoan(int age, int income) {
        super(age, income);
        this.setLoan(DEFAULT_LOAN);
    }

    @Override
    public void calculateLoan() {
        if (this.getAge() > AGE && this.getIncome() > this.getLoan() / 2) {
            int newLoan = this.getLoan()* MULTIPLIER;
            this.setLoan(newLoan);
        }
    }
}
