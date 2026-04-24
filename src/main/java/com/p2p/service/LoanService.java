package com.p2p.service;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.p2p.domain.Borrower; 
import com.p2p.domain.Loan;
public class LoanService {
    // inisialisasi logger
    private static final Logger logger = LogManager.getLogger(LoanService.class);

    public Loan createLoan(Borrower borrower, BigDecimal amount) {
        logger.info("Membuat loan baru...");

        // =========================
        // VALIDASI (delegasi ke domain)
        // =========================
        validateBorrower(borrower);

        // =========================
        // CREATE LOAN (domain object)
        // =========================
        Loan loan = new Loan();

        // =========================
        // BUSINESS ACTION (domain behavior)
        // =========================
        if (borrower.getCreditScore() >= 600) {
            loan.approve();
        } else {
            loan.reject();
        }

        return loan;
    }

    // =========================
    // PRIVATE VALIDATION METHOD
    // =========================
    private void validateBorrower(Borrower borrower) {
        if (!borrower.canApplyLoan()) {
            logger.error("Pembuatan loan gagal: Borrower belum terverifikasi KYC.");
            throw new IllegalArgumentException("Borrower not verified");
        }
        logger.info("Validasi Borrower: Berhasil.");
    }


}
