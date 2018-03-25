package com.abbitt.landbay.borrower;

import com.abbitt.landbay.domain.Loan;
import com.abbitt.landbay.exceptions.LoanMissingException;
import com.abbitt.landbay.lender.InvestmentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public final class BorrowerRestController {
    private static final Logger LOG = LoggerFactory.getLogger(BorrowerRestController.class);

    private final LoanCache loanCache;
    private final InvestmentManager investmentManager;
    private final AtomicLong loanIds = new AtomicLong(1);

    public BorrowerRestController(LoanCache loanCache, InvestmentManager investmentManager) {
        this.loanCache = loanCache;
        this.investmentManager = investmentManager;
    }

    @RequestMapping("/borrower/requestLoan")
    public long requestLoan(@RequestParam(value="accountId") long accountId,
                            @RequestParam(value="amount") double amount,
                            @RequestParam(value="interestRate") double interestRate) {
        LOG.info("Account {} requested loan for {}", accountId, amount);
        long loanId = loanIds.getAndIncrement();
        Loan loan = new Loan(loanId, accountId, amount, interestRate > 1 ? interestRate / 100 : interestRate);
        loanCache.addLoan(loan);
        LOG.info("Loan added with id {}", loanId);
        investmentManager.recalculatePendingInvestments();
        return loanId; // Loan report?
    }

    @RequestMapping("/borrower/getLoan")
    public Loan getLoan(@RequestParam(value="loanId") long loanId) {
        return loanCache.getLoan(loanId).orElse(null);
    }

    @RequestMapping("/borrower/deleteLoan")
    public void deleteLoan(@RequestParam(value="loanId") long loanId) {
        try {
            loanCache.deleteLoan(loanId);
        } catch (LoanMissingException e) {
            LOG.error("Unable to remove loan with ID {}", loanId);
        }
    }
}
