package com.abbitt.landbay.lender;

import com.abbitt.landbay.borrower.LoanCache;
import com.abbitt.landbay.domain.Investment;
import com.abbitt.landbay.domain.Loan;
import com.abbitt.landbay.domain.LoanPart;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class InvestmentManagerImpl implements InvestmentManager {

    private final LoanCache loanCache;

    public InvestmentManagerImpl(LoanCache loanCache) {
        this.loanCache = loanCache;
    }

    @Override
    public void newInvestment(Investment investment) {
        Set<Loan> availableLoans = loanCache.getLoans().stream()
                                        .filter(loan -> !loan.isFullyInvested())
                                        .collect(Collectors.toSet());

        double sumInvested = 0;
        Iterator<Loan> iterator = availableLoans.iterator();
        while (iterator.hasNext() && sumInvested < investment.getAmount()) {
            Loan loan = iterator.next();

            double loanInvestmentAmount = loan.getAvailableAmount() <= investment.getAmount() ?
                                          loan.getAvailableAmount() :
                                          investment.getAmount();

            LoanPart part = new LoanPart(investment, loanInvestmentAmount, 0.0);
            loan.addPart(part);
            sumInvested += loanInvestmentAmount;
        }
    }
}
