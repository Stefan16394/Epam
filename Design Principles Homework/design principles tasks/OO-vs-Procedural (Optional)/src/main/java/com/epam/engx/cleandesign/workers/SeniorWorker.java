package com.epam.engx.cleandesign.workers;

public class SeniorWorker extends Worker {
    private static final double SENIOR_SALARY_FACTOR = 1.2;
    private static final double SENIOR_BONUS_FACTOR = 1.5;

    public SeniorWorker(double dailyRate, double amountPerDay) {
        super(dailyRate, amountPerDay);
    }

    @Override
    public double getSalaryFactor() {
        return SENIOR_SALARY_FACTOR;
    }

    @Override
    public double getBonusFactor() {
        return SENIOR_BONUS_FACTOR;
    }
}
