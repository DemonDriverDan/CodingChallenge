package com.abbitt.landbay.domain;

public class LoanPart {

    private final Investment investment;
    private final double amountInvested;
    private final double percentageReturn;

    public LoanPart(Investment investment, double amountInvested, double percentageReturn) {
        this.investment = investment;
        this.amountInvested = amountInvested;
        this.percentageReturn = percentageReturn;
    }

    public Investment getInvestment() {
        return investment;
    }

    public double getAmountInvested() {
        return amountInvested;
    }

    public double getPercentageReturn() {
        return percentageReturn;
    }
}
