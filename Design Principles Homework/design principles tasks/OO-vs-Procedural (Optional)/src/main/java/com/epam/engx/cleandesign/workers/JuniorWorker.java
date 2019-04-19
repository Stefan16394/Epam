package com.epam.engx.cleandesign.workers;

public class JuniorWorker extends Worker {
    private static final double JUNIOR_SALARY_FACTOR = 1.0;
    private static final double JUNIOR_BONUS_FACTOR = 1.0;

    public JuniorWorker(double dailyRate, double amountPerDay) {
        super(dailyRate,amountPerDay);
    }

    @Override
    public double getSalaryFactor() {
        return JUNIOR_SALARY_FACTOR;
    }

    @Override
    public double getBonusFactor() {
        return JUNIOR_BONUS_FACTOR;
    }

}
