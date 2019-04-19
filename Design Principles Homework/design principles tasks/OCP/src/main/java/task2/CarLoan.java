package task2;

public class CarLoan extends LoanCalculator {
    private static final int DEFAULT_LOAN = 2_000;
    private static final int UPPER_AGE_LIMIT = 50;
    private static final int LOWER_AGE_LIMIT = 30;
    private static final int LOAN_INCREASE_UPPER_AGE_LIMIT = 1_500;
    private static final int LOAN_INCREASE_LOWER_AGE_LIMIT = 1_000;

    public CarLoan(int age, int income) {
        super(age, income);
        this.setLoan(DEFAULT_LOAN);
    }

    @Override
    public void calculateLoan() {
        if (this.getAge() > UPPER_AGE_LIMIT) {
            this.increaseLoan(LOAN_INCREASE_UPPER_AGE_LIMIT);
        } else if (this.getAge() > LOWER_AGE_LIMIT) {
            this.increaseLoan(LOAN_INCREASE_LOWER_AGE_LIMIT); ;
        }
    }
}
