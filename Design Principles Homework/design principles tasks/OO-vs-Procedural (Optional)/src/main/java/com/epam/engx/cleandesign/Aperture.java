package com.epam.engx.cleandesign;

public class Aperture extends Shape {

    public Aperture(double height, double width) {
        super(height,width);
    }

    @Override
    public double calculateArea() {
        return getWidth() * getHeight();
    }

}
