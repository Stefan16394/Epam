package task2;

abstract class LoanCalculator {
    private int loan;
    private int age;
    private int income;

    public LoanCalculator(int age, int income) {
        this.age = age;
        this.income = income;
    }

    public void setLoan(int loan) {
        this.loan = loan;
    }

    public void increaseLoan(int loan) {
        this.loan += loan;
    }

    public int getAge() {
        return age;
    }

    public int getIncome() {
        return income;
    }

    public int getLoan() {
        return loan;
    }

    public abstract void calculateLoan();

    public int takeLoan() {
        this.calculateLoan();
        return IncomeMultiplier.multiply(loan, income);
    }
}
