package com.abbitt.landbay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class BorrowerRestController {
    private static final Logger LOG = LoggerFactory.getLogger(BorrowerRestController.class);

    @RequestMapping("/borrower/requestLoan")
    public void requestLoan(@RequestParam(value="accountId") long accountId,
                            @RequestParam(value="amount") double amount) {
        LOG.info("Account {} requested loan for {}", accountId, amount);

    }

}
