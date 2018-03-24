package com.abbitt.landbay.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Loan {

    private final long id;
    private final long borrowerAccountId;
    private final double amount;
    private final double interestRate;
    private final Set<LoanPart> parts;
    private double investedAmount;

    public Loan(long id, long borrowerAccountId, double amount, double interestRate) {
        this.id = id;
        this.borrowerAccountId = borrowerAccountId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.parts = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public long getBorrowerAccountId() {
        return borrowerAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public Set<LoanPart> getParts() {
        return parts;
    }

    public void addPart(LoanPart part) {
        parts.add(part);
        investedAmount += part.getAmountInvested();
    }

    public double getAvailableAmount() {
        return amount - investedAmount;
    }

    public boolean isFullyInvested() {
        return amount == investedAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Loan loan = (Loan) o;
        return id == loan.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Inefficient
    }
}
