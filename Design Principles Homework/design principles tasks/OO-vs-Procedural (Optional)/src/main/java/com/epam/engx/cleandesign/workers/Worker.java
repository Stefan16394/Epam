package com.epam.engx.cleandesign.workers;

public abstract class Worker {
    private double dailyRate;
    private double amountPerDay;

    public Worker(double dailyRate, double amountPerDay) {
        this.dailyRate = dailyRate;
        this.amountPerDay = amountPerDay;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public double getAmountPerDay() {
        return amountPerDay;
    }

    public double calculateSalary(double area) {
        int days = (int) Math.ceil(area / this.amountPerDay);
        double baseSalary = this.dailyRate * days;
        return getSalaryFactor() * baseSalary;
    }

    public abstract double getSalaryFactor();

    public abstract double getBonusFactor();
}
