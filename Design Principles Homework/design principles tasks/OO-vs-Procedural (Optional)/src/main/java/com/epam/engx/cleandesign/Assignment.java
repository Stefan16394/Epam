package com.epam.engx.cleandesign;

import com.epam.engx.cleandesign.workers.Worker;

import java.util.List;

public class Assignment {
    private Worker worker;
    private List<Zone> zones;
    private double vendorBonus;

    public void setVendorBonus(double vendorBonus) {
        this.vendorBonus = vendorBonus;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public double getVendorBonus() {
        return vendorBonus;
    }

    public double getAssignmentBonus() {
        double workerBonus  = this.worker.getBonusFactor();
        return this.vendorBonus * workerBonus;
    }
}
