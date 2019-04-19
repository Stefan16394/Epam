package task2;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LoanCalculatorTest {

    private LoanCalculator loanCalculator;

    @Test
    public void studentLoanForYoungPoorPerson() {
        assertThat(new StudentLoan(18, 100).takeLoan(), is(100));
    }

    @Test
    public void studentLoanForOldPerson() {
        loanCalculator = new StudentLoan(21,100);
        assertThat(loanCalculator.takeLoan(), is(250));
    }

    @Test
    public void studentLoanForRichPerson() {
        loanCalculator = new StudentLoan(18, 2_000);
        assertThat(loanCalculator.takeLoan(), is(200));
    }

    @Test
    public void carLoanForYoungPoorPerson() {
       loanCalculator = new CarLoan(20, 500);
        assertThat(loanCalculator.takeLoan(), is(2_000));
    }

    @Test
    public void carLoanForAdultPoorPerson() {
        loanCalculator = new CarLoan(45, 500);
        assertThat(loanCalculator.takeLoan(), is(3_000));
    }

    @Test
    public void carLoanForOldPoorPerson() {
        loanCalculator = new CarLoan(60, 500);
        assertThat(loanCalculator.takeLoan(), is(3_500));
    }

    @Test
    public void carLoanForYoungRichPerson() {
        loanCalculator = new CarLoan(20, 2_000);
        assertThat(loanCalculator.takeLoan(), is(4_000));
    }

    @Test
    public void carLoanForAdultRichPerson() {
        loanCalculator = new CarLoan(45, 2_000);
        assertThat(loanCalculator.takeLoan(), is(6_000));
    }

    @Test
    public void carLoanForOldRichPerson() {
        loanCalculator = new CarLoan(60, 2_000);
        assertThat(loanCalculator.takeLoan(), is(7_000));
    }

    @Test
    public void houseLoanForYoungPoorPerson() {
        loanCalculator = new HouseLoan(20, 500);
        assertThat(loanCalculator.takeLoan(), is(100_000));
    }

    @Test
    public void houseLoanForOldPoorPerson() {
        loanCalculator = new HouseLoan(60, 500);
        assertThat(loanCalculator.takeLoan(), is(100_000));
    }

    @Test
    public void houseLoanForYoungRichPerson() {
        loanCalculator = new HouseLoan(20, 65_000);
        assertThat(loanCalculator.takeLoan(), is(200_000));
    }

    @Test
    public void houseLoanForOldRichPerson() {
        loanCalculator = new HouseLoan(60, 65_000);
        assertThat(loanCalculator.takeLoan(), is(400_000));
    }
}
