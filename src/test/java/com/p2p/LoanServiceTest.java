package com.p2p;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.p2p.domain.Borrower;
import com.p2p.domain.Loan;
import com.p2p.service.LoanService;

public class LoanServiceTest {
    // Inisialisasi logger
    private static final Logger logger = LogManager.getLogger(LoanService.class);
    
    // =====================================================
    // TEST CASE TC-01
    // =====================================================
    @Test
    void shouldRejectLoanWhenBorrowerNotVerified() {
        
        // =====================================================
        // SCENARIO:
        // Borrower tidak terverifikasi (KYC = false)
        // Ketika borrower mengajukan pinjaman
        // Maka sistem harus menolak dengan melempar exception
        // =====================================================
        
        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower belum lolos proses KYC
        Borrower borrower = new Borrower(false, 700);
        
        // Service untuk pengajuan loan
        LoanService loanService = new LoanService();
        
        // Jumlah pinjaman valid
        BigDecimal amount = BigDecimal.valueOf(1000);
        
        // =========================
        // Act (Action) + Assert
        // =========================
        // Borrower mencoba mengajukan loan
        // sistem menolak dengan melempar exception
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount); 
        });
        
        // =========================
        // Assert (Expected Result)
        // =========================
        assertTrue(true);
    }
    

    // =====================================================
    // TEST CASE TC-02
    // =====================================================
    @Test
    void shouldRejectLoanWhenAmountIsZeroOrNegative(){
        // =====================================================
        // SCENARIO:
        // Borrower terverifikasi tetapi amount ≤ 0
        // Ketika borrower mengajukan pinjaman
        // Maka sistem harus menolak dengan melempar exception
        // =====================================================

        Borrower borrower = new Borrower(true, 700);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(0);

        // =========================
        // Act (Action)
        // =========================
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount); 
        });

        assertTrue(true);
    
    }

    // =====================================================
    // TEST CASE TC-03
    // =====================================================
    @Test
    void shouldApproveLoanWhenCreditScoreHigh(){
        // =====================================================
        // SCENARIO:
        // Borrower terverifikasi dan credit score ≥ threshold
        // Ketika borrower mengajukan pinjaman
        // Maka sistem memberikan keputusan "APPROVED"
        // =====================================================

        Borrower borrower = new Borrower(true, 700);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);

        // =========================
        // Act (Action)
        // =========================
        Loan loan = loanService.createLoan(borrower, amount);

        assertEquals(Loan.Status.APPROVED, loan.getStatus());
    
    }
    
    // =====================================================
    // TEST CASE TC-04
    // =====================================================
    @Test
    void shouldRejectLoanWhenCreditScoreLow(){
        // =====================================================
        // SCENARIO:
        // Borrower terverifikasi dan credit score < threshold
        // Ketika borrower mengajukan pinjaman
        // Maka sistem memberikan keputusan "REJECTED"
        // =====================================================

        Borrower borrower = new Borrower(true, 500);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);

        // =========================
        // Act (Action)
        // =========================
        Loan loan = loanService.createLoan(borrower, amount);

        assertEquals(Loan.Status.REJECTED, loan.getStatus());
    }
}
