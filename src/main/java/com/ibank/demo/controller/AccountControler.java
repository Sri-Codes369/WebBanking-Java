package com.ibank.demo.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibank.demo.dto.AccCreationDTO;
import com.ibank.demo.dto.AccountDetailsDTO;
import com.ibank.demo.dto.AllAccountDetailsDTO;
import com.ibank.demo.dto.ApiResponseDTO;
import com.ibank.demo.dto.UpdateAccountStatusDTO;
import com.ibank.demo.service.AccountService;

@RestController
@RequestMapping("/api")
public class AccountControler {
 @Autowired
    private AccountService accountKycService;

     @PostMapping("/acc-insert")
    public ResponseEntity<Map<String, Object>> insertAccountAndKyc(@RequestBody AccCreationDTO accountKycDTO) {
        try {
            // The service returns a Map containing the response code and message
            Map<String, Object> result = accountKycService.insertAccountAndKyc(accountKycDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Prepare error response in case of exception
            Map<String, Object> errorResponse = Map.of(
                "Response Code", 500,
                "Message", "Error: " + e.getMessage()
            );
            return ResponseEntity.status(500).body(errorResponse);
        }
    };

    @GetMapping("/account-details")
    public ResponseEntity<List<AccountDetailsDTO>> getAccountDetails(@RequestParam int userId) {
        List<AccountDetailsDTO> accountDetails = accountKycService.getUserAccountDetails(userId);
        return ResponseEntity.ok(accountDetails);
    }

    @GetMapping("/allAccount-details")
    public ResponseEntity<List<AllAccountDetailsDTO>> getAllAccountDetails(@RequestParam int queryType,@RequestParam int accountId) {
        List<AllAccountDetailsDTO> accountDetails = accountKycService.getAllAccountDetails(queryType,accountId);
        return ResponseEntity.ok(accountDetails);
    }

    @PutMapping("/updateAccStatus")
    public ResponseEntity<ApiResponseDTO> updateAccountStatus(@RequestBody UpdateAccountStatusDTO updateAccountStatusDTO) {
        ApiResponseDTO response = accountKycService.updateAccountStatus(updateAccountStatusDTO);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
