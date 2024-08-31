package com.ibank.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibank.demo.dto.AccountTypeDTO;
import com.ibank.demo.dto.KYCOptionDTO;
import com.ibank.demo.service.LookUpService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LookUpController {

    @Autowired
    private LookUpService lookupService;

    @GetMapping("/account-types")
    public ResponseEntity<?> getAccountTypes() {
         Map<String, Object> response = new HashMap<>();
        try {
            List<AccountTypeDTO> accountTypes = lookupService.getAllAccountTypes();
            // return new ResponseEntity<>(accountTypes, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(accountTypes);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            response.put("ResponseCode", -500);
            response.put("Response", "Error registering user: " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/kyc-options")
    public ResponseEntity<?> getKYCOptions() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<KYCOptionDTO> kycOptions = lookupService.getAllKYCOptions();
            return ResponseEntity.status(HttpStatus.OK).body(kycOptions);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            response.put("ResponseCode", -500);
            response.put("Response", "Error registering user: " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
