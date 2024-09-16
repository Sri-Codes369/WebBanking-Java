package com.ibank.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ibank.demo.dto.TransactionRequestDTO;
import com.ibank.demo.service.TransactionService;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<Map<String, Object>> createTransaction(
        @RequestBody TransactionRequestDTO request
    ) {
        Map<String, Object> result = transactionService.createTransaction(
            request.getSenderAccountId(),
            request.getRecipientAccountNumber(),
            request.getRecipientIFSC(),
            request.getAmount()
        );
        return ResponseEntity.ok(result);
    }
}

