package com.abbitt.landbay.lender;

import com.abbitt.landbay.borrower.LoanCache;
import com.abbitt.landbay.domain.Investment;
import com.abbitt.landbay.domain.Loan;
import com.abbitt.landbay.domain.LoanPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class InvestmentManagerImpl implements InvestmentManager {
    private static final Logger LOG = LoggerFactory.getLogger(InvestmentManagerImpl.class);

    private final LoanCache loanCache;
    private final Set<Investment> pendingInvestments;
    private final Set<Investment> fulfilledInvestments;

    public InvestmentManagerImpl(LoanCache loanCache) {
        this.loanCache = loanCache;
        this.pendingInvestments = new HashSet<>();
        this.fulfilledInvestments = new HashSet<>();
    }

    @Override
    public void newInvestment(Investment investment) {
        Set<Loan> availableLoans = loanCache.getLoans().stream()
                                        .filter(loan -> !loan.isFullyInvested())
                                        .collect(Collectors.toSet());

        double totalAvailableLoanAmount = availableLoans.stream().mapToDouble(Loan::getAvailableAmount).sum();
        if (totalAvailableLoanAmount < investment.getAmount()) {
            LOG.info("Unable to invest as loans of {} will not fulfill investment of {}", totalAvailableLoanAmount, investment.getAmount());
            pendingInvestments.add(investment);
            return;
        }

        LOG.info("Total loans available {}", totalAvailableLoanAmount);

        double sumInvested = 0;
        Iterator<Loan> iterator = availableLoans.iterator();
        while (iterator.hasNext() && sumInvested < investment.getAmount()) {
            Loan loan = iterator.next();

            double loanInvestmentAmount = calculateLoanInvestmentAmount(loan.getAvailableAmount(), investment.getAmount(),
                                                                        totalAvailableLoanAmount);

            LoanPart part = new LoanPart(investment, loanInvestmentAmount, 0.0);
            loan.addPart(part);
            LOG.info("Invested {} in loan {}", loanInvestmentAmount, loan.getId());
            sumInvested += loanInvestmentAmount;
        }
        LOG.info("Investment of {} fulfilled", investment.getAmount());
        fulfilledInvestments.add(investment);
    }

    @Override
    public void recalculatePendingInvestments() {
        pendingInvestments.forEach(this::newInvestment);
    }

    private static double calculateLoanInvestmentAmount(double loanAvailableAmount, double investmentAmount, double totalLoanAmounts) {
        // Splits investment across all available loans based on their percentage of the total loan amount
        return Math.round((loanAvailableAmount / totalLoanAmounts) * investmentAmount);
    }
}
