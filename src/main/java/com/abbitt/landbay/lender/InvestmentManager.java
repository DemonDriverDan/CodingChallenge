package com.abbitt.landbay.lender;

import com.abbitt.landbay.domain.Investment;

import java.util.Map;

public interface InvestmentManager {

    InvestmentReport newInvestment(Investment investment);

    void recalculatePendingInvestments();

    Map<Long, Double> calculateInterestOwedPerInvestor();
}
