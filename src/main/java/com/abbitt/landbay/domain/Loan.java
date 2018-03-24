package com.abbitt.landbay.domain;

import java.util.Objects;

public class Loan {

    private final long id;
    private final long borrowerAccountId;
    private final double amount;
    private final double interestRate;

    public Loan(long id, long borrowerAccountId, double amount, double interestRate) {
        this.id = id;
        this.borrowerAccountId = borrowerAccountId;
        this.amount = amount;
        this.interestRate = interestRate;
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
