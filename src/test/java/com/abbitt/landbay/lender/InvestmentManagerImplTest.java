package com.abbitt.landbay.lender;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import com.abbitt.landbay.borrower.LoanCache;
import com.abbitt.landbay.domain.Investment;
import com.abbitt.landbay.domain.Loan;
import com.abbitt.landbay.domain.LoanPart;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Test
public class InvestmentManagerImplTest {

    @Mock
    private LoanCache loanCache;

    private InvestmentManagerImpl manager;

    private Loan loan;
    private Set<Loan> loans;

    @BeforeMethod
    public void setup() {
        initMocks(this);
        manager = new InvestmentManagerImpl(loanCache);

        loan = new Loan(1L, 1L, 5.0, 0);
        loans = new HashSet<>();
        when(loanCache.getLoans()).thenReturn(loans);
    }

    public void shouldAddInvestmentToLoan() {
        double investmentAmount = 100;
        Investment investment = new Investment(1L, 1L, investmentAmount, 0.0);
        loan = new Loan(1L, 1L, investmentAmount, 0.0);
        loans.add(loan);

        manager.newInvestment(investment);

        assertEquals(loan.getParts().size(), 1);
        LoanPart part = loan.getParts().iterator().next();
        assertEquals(part.getInvestment(), investment);
        assertEquals(part.getAmountInvested(), investmentAmount);
    }

    public void shouldAddInvestmentToMultipleLoans() {
        double investmentAmount = 100;
        Investment investment = new Investment(1L, 1L, investmentAmount, 0.0);
        loan = new Loan(1L, 1L, investmentAmount / 2, 0.0);
        loans.add(loan);
        Loan loan2 = new Loan(2L, 1L, investmentAmount / 2, 0.0);
        loans.add(loan2);

        manager.newInvestment(investment);

        assertEquals(loan.getParts().size(), 1);
        LoanPart part = loan.getParts().iterator().next();
        assertEquals(part.getInvestment(), investment);
        assertEquals(part.getAmountInvested(), investmentAmount / 2);

        assertEquals(loan2.getParts().size(), 1);
        part = loan2.getParts().iterator().next();
        assertEquals(part.getInvestment(), investment);
        assertEquals(part.getAmountInvested(), investmentAmount / 2);
    }

    public void shouldLeaveUnInvestedAmountInLoanIfInvestmentLessThanLoanAmount() {
        double investmentAmount = 50;
        Investment investment = new Investment(1L, 1L, investmentAmount, 0.0);
        loan = new Loan(1L, 1L, investmentAmount * 2, 0.0);
        loans.add(loan);

        manager.newInvestment(investment);

        assertEquals(loan.getAvailableAmount(), investmentAmount);
        assertFalse(loan.isFullyInvested());
        assertEquals(loan.getParts().size(), 1);
        LoanPart part = loan.getParts().iterator().next();
        assertEquals(part.getInvestment(), investment);
        assertEquals(part.getAmountInvested(), investmentAmount);
    }

    public void shouldNotInvestIfLoansDoNotFillEntireAmount() {
        double investmentAmount = 100;
        Investment investment = new Investment(1L, 1L, investmentAmount, 0.0);
        loan = new Loan(1L, 1L, investmentAmount / 2, 0.0);
        loans.add(loan);

        manager.newInvestment(investment);

        assertEquals(loan.getParts().size(), 0);
    }

    public void shouldDiversifyInvestmentAcrossAllAvailableLoans() {
        double investmentAmount = 100;
        Investment investment = new Investment(1L, 1L, investmentAmount, 0.0);
        loan = new Loan(1L, 1L, 50, 0.0);
        loans.add(loan);
        Loan loan2 = new Loan(2L, 1L, 100, 0.0);
        loans.add(loan2);
        Loan loan3 = new Loan(3L, 1L, 50, 0.0);
        loans.add(loan3);

        manager.newInvestment(investment);

        assertEquals(loan.getParts().size(), 1);
        LoanPart part = loan.getParts().iterator().next();
        assertEquals(part.getAmountInvested(), 25.0);

        assertEquals(loan2.getParts().size(), 1);
        part = loan2.getParts().iterator().next();
        assertEquals(part.getAmountInvested(), 50.0);

        assertEquals(loan3.getParts().size(), 1);
        part = loan3.getParts().iterator().next();
        assertEquals(part.getAmountInvested(), 25.0);
    }

    public void shouldFulfillInvestmentWhenNewLoanArrives() {
        double investmentAmount = 50;
        Investment investment = new Investment(1L, 1L, investmentAmount, 0.0);
        loan = new Loan(1L, 1L, investmentAmount / 2, 0.0);
        loans.add(loan);

        manager.newInvestment(investment);

        assertEquals(loan.getAvailableAmount(), investmentAmount / 2);

        Loan loan2 = new Loan(2L, 1L, investmentAmount / 2, 0.0);
        loans.add(loan2);

        manager.recalculatePendingInvestments();

        assertEquals(loan.getAvailableAmount(), 0.0);
        assertTrue(loan.isFullyInvested());
        assertEquals(loan2.getAvailableAmount(), 0.0);
        assertTrue(loan2.isFullyInvested());
    }

    public void shouldReturnInterestOnInvestment() {
        double investmentAmount = 50;
        double interestRate = 0.05;
        Investment investment = new Investment(1L, 1L, investmentAmount, 0.0);
        loan = new Loan(1L, 1L, investmentAmount, interestRate);
        loans.add(loan);
        manager.newInvestment(investment);

        Map<Long, Double> interestOwedPerInvestor = manager.calculateInterestOwedPerInvestor();
        assertEquals(interestOwedPerInvestor.size(), 1);
        assertEquals(interestOwedPerInvestor.get(1L), 2.5);
    }

    public void shouldCalculateInterestAcrossMultipleLoans() {
        double investmentAmount = 100;
        Investment investment = new Investment(1L, 1L, investmentAmount, 0.0);
        loan = new Loan(1L, 1L, 50, 0.05);
        loans.add(loan);
        Loan loan2 = new Loan(2L, 1L, 100, 0.015);
        loans.add(loan2);
        Loan loan3 = new Loan(3L, 1L, 50, 0.2);
        loans.add(loan3);
        manager.newInvestment(investment);

        Map<Long, Double> interestOwedPerInvestor = manager.calculateInterestOwedPerInvestor();
        assertEquals(interestOwedPerInvestor.size(), 1);
        assertEquals(interestOwedPerInvestor.get(1L), 7.0);
    }
}
