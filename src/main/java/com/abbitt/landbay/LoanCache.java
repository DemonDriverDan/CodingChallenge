package com.abbitt.landbay;

import com.abbitt.landbay.domain.Loan;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public final class LoanCache {

    private final Set<Loan> loans;

    public LoanCache() {
        loans = new HashSet<>();
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public Optional<Loan> getLoan(long id) {
        return loans.stream().filter(loan -> loan.getId() == id).findFirst();
    }
}
