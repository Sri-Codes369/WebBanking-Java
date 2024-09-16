package com.ibank.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ibank.demo.dto.BeneficiaryDTO;
import com.ibank.demo.dto.BeneficiaryFetchDTO;
import com.ibank.demo.dto.BeneficiaryTypeDTO;
import com.ibank.demo.service.BeneficiaryService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    @PostMapping("/beneficiary")
    public ResponseEntity<String> addBeneficiary(@RequestBody BeneficiaryDTO beneficiary) {
        try {
            beneficiaryService.addBeneficiary(beneficiary);
            return ResponseEntity.ok("Beneficiary added successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error adding beneficiary: " + e.getMessage());
        }
    }


    // Get a specific beneficiary by ID for a user
    @GetMapping("/beneficiary/{queryType}/{userId}/{beneficiaryId}")
    public ResponseEntity<List<BeneficiaryFetchDTO>> getBeneficiary(@PathVariable int queryType,@PathVariable int userId, @PathVariable int beneficiaryId) {
        try {
            List<BeneficiaryFetchDTO> beneficiary = beneficiaryService.getBeneficiaries(queryType, userId, beneficiaryId);
            if (beneficiary != null) {
                return ResponseEntity.ok(beneficiary);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/beneficiary/{beneficiaryId}")
    public ResponseEntity<String> updateBeneficiary(@PathVariable int beneficiaryId, @RequestBody BeneficiaryDTO beneficiary) {
        try {
            beneficiary.setBeneficiaryId(beneficiaryId);
            beneficiaryService.updateBeneficiary(beneficiary);
            return ResponseEntity.ok("Beneficiary updated successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error updating beneficiary: " + e.getMessage());
        }
    }

    @GetMapping("/beneficiary/types")
    public ResponseEntity<List<BeneficiaryTypeDTO>> getAllBeneficiaryTypes() {
        try {
            List<BeneficiaryTypeDTO> beneficiaryTypes = beneficiaryService.getAllBeneficiaryTypes();
            return ResponseEntity.ok(beneficiaryTypes);
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/beneficiary/{id}")
    public ResponseEntity<String> softDeleteBeneficiary(@PathVariable("id") int beneficiaryId) {
        boolean isDeleted = beneficiaryService.softDeleteBeneficiary(beneficiaryId);
        if (isDeleted) {
            return ResponseEntity.ok("Beneficiary soft deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Beneficiary not found or already deleted.");
        }
    }
}