package com.abbitt.landbay.lender;

import com.abbitt.landbay.domain.Investment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public final class LenderRestController {
    private static final Logger LOG = LoggerFactory.getLogger(LenderRestController.class);

    private final InvestmentManager investmentManager;
    private final AtomicLong investmentIds = new AtomicLong(1);

    public LenderRestController(InvestmentManager investmentManager) {
        this.investmentManager = investmentManager;
    }

    @RequestMapping("/lender/invest")
    public InvestmentReport invest(@RequestParam(value="accountId") long accountId,
                       @RequestParam(value="amount") double amount) {
        LOG.info("Account {} requested investment of {}", accountId, amount);
        long id = investmentIds.getAndIncrement();

        Investment investment = new Investment(id, accountId, amount, 0.0);
        return investmentManager.newInvestment(investment);
    }

}
