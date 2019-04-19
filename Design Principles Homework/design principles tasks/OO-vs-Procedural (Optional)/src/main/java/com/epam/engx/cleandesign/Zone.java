package com.epam.engx.cleandesign;

import java.util.ArrayList;
import java.util.List;

import static com.epam.engx.cleandesign.Utils.CalculationUtil.sumAllValues;

public class Zone extends Shape {

    private String type;
    private List<Aperture> apertures = new ArrayList<>();

    public Zone(String type, double height, double width) {
        super(height,width);
        this.type = type;
    }

    public void setApertures(List<Aperture> apertures) {
        this.apertures = apertures;
    }

    public List<Aperture> getApertures() {
        return apertures;
    }

    public String getType() {
        return type;
    }

    @Override
    public double calculateArea() {
        return getHeight() * getWidth() - sumAllValues(this.apertures, Aperture::calculateArea);
    }
}
