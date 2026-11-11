 
package com.example.bankapp.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.bankapp.model.BankTransaction;
import com.example.bankapp.model.TransactionStatus;
import com.example.bankapp.repository.BankTransactionRepository;

@Service
public class TransactionAuditService {

    private final BankTransactionRepository transactionRepository;

    public TransactionAuditService(
            BankTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordFailure(
            String fromAccount,
            String toAccount,
            BigDecimal amount,
            String description) {

        BankTransaction transaction = new BankTransaction(
                fromAccount,
                toAccount,
                amount,
                TransactionStatus.FAILED,
                description
        );

        transactionRepository.save(transaction);
    }
}
