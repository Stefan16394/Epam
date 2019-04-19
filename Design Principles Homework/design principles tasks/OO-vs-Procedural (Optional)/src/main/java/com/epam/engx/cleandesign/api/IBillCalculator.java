package com.epam.engx.cleandesign.api;

import com.epam.engx.cleandesign.Zone;

import java.util.Map;

public interface IBillCalculator {
    Double calculateZoneBillPrice(Zone zone);

    void setZoneTypeWorkPrice(Map<String, Double> zoneTypeWorkPrice);
}
