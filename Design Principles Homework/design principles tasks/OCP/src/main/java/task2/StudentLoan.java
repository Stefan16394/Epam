package task2;

public class StudentLoan extends LoanCalculator {
    private static final int AGE = 21;
    private static final int LOAN_INCREASE_AMOUNT = 150;
    private static final int DEFAULT_LOAN = 100;

    public StudentLoan(int age, int income) {
        super(age, income);
        this.setLoan(DEFAULT_LOAN);
    }

    @Override
    public void calculateLoan() {
        if (this.getAge() >= AGE) {
            this.increaseLoan(LOAN_INCREASE_AMOUNT);
        }
    }

}
