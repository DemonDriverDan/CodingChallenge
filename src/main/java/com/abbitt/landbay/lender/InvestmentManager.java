package com.abbitt.landbay.lender;

import com.abbitt.landbay.domain.Investment;

public interface InvestmentManager {

    InvestmentReport newInvestment(Investment investment);

    void recalculatePendingInvestments();
}
