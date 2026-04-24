package com.p2p;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.p2p.domain.Borrower;
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
    

    
}
