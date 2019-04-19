package com.epam.engx.cleandesign.api;

import com.epam.engx.cleandesign.Assignment;

import java.util.List;

public interface IFundCalculator {
    double getFundBalance(List<Assignment> assignments);

    void setBillCalculator(IBillCalculator billCalculator);

}
