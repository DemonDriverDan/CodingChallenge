package com.abbitt.landbay;

import com.abbitt.landbay.domain.Loan;
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
    private final AtomicLong loanIds = new AtomicLong(1);

    public BorrowerRestController(LoanCache loanCache) {
        this.loanCache = loanCache;
    }

    @RequestMapping("/borrower/requestLoan")
    public void requestLoan(@RequestParam(value="accountId") long accountId,
                            @RequestParam(value="amount") double amount) {
        LOG.info("Account {} requested loan for {}", accountId, amount);
        long loanId = loanIds.getAndIncrement();
        Loan loan = new Loan(loanId, accountId, amount, 0.0);
        loanCache.addLoan(loan);
        LOG.info("Loan added with id {}", loanId);
        // TODO Return
    }

    @RequestMapping("/borrower/getLoan")
    public Loan getLoan(@RequestParam(value="loanId") long loanId) {
        return loanCache.getLoan(loanId).orElse(null);
    }
}
