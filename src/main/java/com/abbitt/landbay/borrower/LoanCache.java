package com.abbitt.landbay.borrower;

import com.abbitt.landbay.domain.Loan;
import com.abbitt.landbay.exceptions.LoanMissingException;
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

    public void deleteLoan(long id) throws LoanMissingException {
        boolean anyRemoved = loans.removeIf(loan -> loan.getId() == id);
        if (!anyRemoved) {
            throw new LoanMissingException(id);
        }
    }
}
