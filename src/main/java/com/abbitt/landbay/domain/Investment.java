package com.abbitt.landbay.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Investment {

    private final long id;
    private final long lenderAccountId;
    private final double amount;
    private final double minimumReturn;
    private final Set<InvestmentPart> investmentParts;

    public Investment(long id, long lenderAccountId, double amount, double minimumReturn) {
        this.id = id;
        this.lenderAccountId = lenderAccountId;
        this.amount = amount;
        this.minimumReturn = minimumReturn;
        this.investmentParts = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public long getLenderAccountId() {
        return lenderAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public double getMinimumReturn() {
        return minimumReturn;
    }

    public Set<InvestmentPart> getInvestmentParts() {
        return investmentParts;
    }

    public void addInvestmentPart(InvestmentPart part) {
        investmentParts.add(part);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Investment that = (Investment) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Inefficient
    }
}
