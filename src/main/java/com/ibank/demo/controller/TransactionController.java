package com.ibank.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ibank.demo.dto.TransactionDetailsDTO;
import com.ibank.demo.dto.TransactionRequestDTO;
import com.ibank.demo.service.TransactionService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
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

   @GetMapping("/transactions")
public ResponseEntity<List<TransactionDetailsDTO>> getTransactionDetails(@RequestParam int queryType, @RequestParam int userId) {
    try {
        // Call service to fetch transaction details
        List<TransactionDetailsDTO> transactions = transactionService.getTransactionDetails(queryType, userId);

        // Return response with the list of transactions
        return ResponseEntity.ok(transactions);
    } catch (SQLException e) {
        // Handle SQL exception and return a proper error response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(Collections.emptyList()); // Or return a custom error message!
    }
}

}

