package com.abbitt.landbay.exceptions;

public class LoanMissingException extends Exception {

    private static final String MESSAGE = "Loan with ID %d is missing";

    public LoanMissingException(long missingLoanId) {
        super(String.format(MESSAGE, missingLoanId));
    }
}
