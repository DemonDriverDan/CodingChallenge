package com.abbitt.landbay.lender;

import com.abbitt.landbay.domain.Investment;

public class InvestmentReport {

    enum State {
        FULFILLED,
        PENDING
    }

    private final Investment initialInvestment;
    private State state = State.PENDING;
    private int numberOfLoans;

    InvestmentReport(Investment initialInvestment) {
        this.initialInvestment = initialInvestment;
    }

    public void setFulfilled() {
        state = State.FULFILLED;
    }

    public void setPending() {
        state = State.PENDING;
    }

    public void addLoan() {
        numberOfLoans++;
    }

    public Investment getInitialInvestment() {
        return initialInvestment;
    }

    public State getState() {
        return state;
    }

    public int getNumberOfLoans() {
        return numberOfLoans;
    }
}
