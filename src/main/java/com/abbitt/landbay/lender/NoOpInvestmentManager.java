package com.abbitt.landbay.lender;

import com.abbitt.landbay.domain.Investment;
import org.springframework.stereotype.Component;

@Component
public final class NoOpInvestmentManager implements InvestmentManager {


    @Override
    public void newInvestment(Investment investment) {

    }
}
