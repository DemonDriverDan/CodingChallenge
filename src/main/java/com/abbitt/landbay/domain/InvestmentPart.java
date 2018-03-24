package com.abbitt.landbay.domain;

public class InvestmentPart {

    private final Loan loan;
    private final double amountInvested;
    private final double percentageReturn;

    public InvestmentPart(Loan loan, double amountInvested, double percentageReturn) {
        this.loan = loan;
        this.amountInvested = amountInvested;
        this.percentageReturn = percentageReturn;
    }

    public Loan getLoan() {
        return loan;
    }

    public double getAmountInvested() {
        return amountInvested;
    }

    public double getPercentageReturn() {
        return percentageReturn;
    }

    // TODO Equals?
}
