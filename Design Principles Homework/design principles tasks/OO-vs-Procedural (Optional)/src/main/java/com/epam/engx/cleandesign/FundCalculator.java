package com.epam.engx.cleandesign;

import com.epam.engx.cleandesign.Utils.AreaUtil;
import com.epam.engx.cleandesign.api.IBillCalculator;
import com.epam.engx.cleandesign.api.IFundCalculator;
import com.epam.engx.cleandesign.workers.Worker;

import java.util.List;

import static com.epam.engx.cleandesign.Utils.CalculationUtil.sumAllValues;

public class FundCalculator implements IFundCalculator {

    private IBillCalculator billCalculator;

    public FundCalculator(){
    }

    public FundCalculator(IBillCalculator billCalculator){
        this.setBillCalculator(billCalculator);
    }

    @Override
    public double getFundBalance(List<Assignment> assignments){
        double salaries = 0.0;
        double bill = 0.0;
        for (Assignment ass : assignments) {
            Worker worker = ass.getWorker();
            List<Zone> zones = ass.getZones();
            double totalArea = sumAllValues(zones, AreaUtil::calculateArea);
            salaries += worker.calculateSalary(totalArea) + ass.getAssignmentBonus();
            bill += sumAllValues(zones, billCalculator::calculateZoneBillPrice);
        }
        return bill - salaries;
    }

    @Override
    public void setBillCalculator(IBillCalculator billCalculator) {
        this.billCalculator = billCalculator;
    }
}
