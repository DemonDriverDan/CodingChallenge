package com.abbitt.landbay.lender;

import com.abbitt.landbay.domain.Investment;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class InvestmentCache {

    private final Set<Investment> investments;

    public InvestmentCache() {
        investments = new HashSet<>(); // Could use TreeSet and sort by amount remaining descending
    }

    public Optional<Investment> getInvestment(long id) {
        return investments.stream().filter(investment -> investment.getId() == id).findFirst();
    }

    public void addInvestment(Investment investment) {
        investments.add(investment);
    }
}
