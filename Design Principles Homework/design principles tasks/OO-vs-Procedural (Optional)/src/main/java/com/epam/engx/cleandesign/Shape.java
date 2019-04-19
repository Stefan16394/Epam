package com.epam.engx.cleandesign;

public abstract class Shape {
    private double height;
    private double width;

    public Shape(double height, double width) {
        this.height = height;
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public abstract double calculateArea();
}
