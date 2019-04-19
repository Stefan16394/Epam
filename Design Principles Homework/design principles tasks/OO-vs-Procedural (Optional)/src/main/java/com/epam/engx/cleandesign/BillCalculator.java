package com.epam.engx.cleandesign;

import com.epam.engx.cleandesign.api.IBillCalculator;

import java.util.HashMap;
import java.util.Map;

import static com.epam.engx.cleandesign.Utils.AreaUtil.calculateArea;

public class BillCalculator implements IBillCalculator {
    private static final double MATERIAL_AREA_FACTOR = 10;
    private static final int ONE_DAY_MAX_AREA = 50;
    private static final double MULTI_DAY_PRICE_FACTOR = 1.1;

    private Map<String, Double> zoneTypeWorkPrice;

    public BillCalculator(Map<String,Double> zoneTypeWorkPrice){
        this.zoneTypeWorkPrice = new HashMap<>(zoneTypeWorkPrice);
    }

    @Override
    public void setZoneTypeWorkPrice(Map<String, Double> zoneTypeWorkPrice) {
        this.zoneTypeWorkPrice = zoneTypeWorkPrice;
    }

    @Override
    public Double calculateZoneBillPrice(Zone zone) {
        validateZone(zone);
        return getZoneBillPrice(zone);
    }

    private Double getZoneBillPrice(Zone zone) {
        double area = calculateArea(zone);
        return getMaterialPrice(area) + getWorkPrice(area, zone.getType());
    }

    private void validateZone(Zone zone) {
        if (isNotContainsKey(zone))
            throw new WrongZoneTypeException("Invalid zone type");
    }

    private boolean isNotContainsKey(Zone zone) {
        return !zoneTypeWorkPrice.keySet().contains(zone.getType());
    }

    private double getMaterialPrice(double area) {
        return area * MATERIAL_AREA_FACTOR;
    }

    private double getWorkPrice(double area, String type) {
        double price = area * zoneTypeWorkPrice.get(type);
        if (area < ONE_DAY_MAX_AREA)
            return price;
        return price * MULTI_DAY_PRICE_FACTOR;
    }
}
