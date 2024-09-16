package com.ibank.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ibank.demo.dto.BeneficiaryDTO;
import com.ibank.demo.dto.BeneficiaryFetchDTO;
import com.ibank.demo.dto.BeneficiaryTypeDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class BeneficiaryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addBeneficiary(BeneficiaryDTO beneficiary) throws SQLException {
        jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement stmt = conn.prepareCall("{CALL SP_insertBeneficiary(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
                stmt.setInt(1, beneficiary.getUserId());
                stmt.setString(2, beneficiary.getName());
                stmt.setString(3, beneficiary.getAccountNumber());
                stmt.setString(4, beneficiary.getBankName());
                stmt.setString(5, beneficiary.getIfscCode());
                stmt.setInt(6, beneficiary.getBeneficiaryType()); // Assuming it's an integer reference
                stmt.setString(7, beneficiary.getNickname());
                stmt.setString(8, beneficiary.getAddress());
                stmt.setString(9, beneficiary.getMobileNumber());
                stmt.setString(10, beneficiary.getEmail());
                stmt.setDouble(11, beneficiary.getTransferLimit());
                stmt.setDate(12, beneficiary.getActivationStart());
                stmt.setDate(13, beneficiary.getActivationEnd());
            // Check if activationStart is an empty string or null
            // if (beneficiary.getActivationStart() != null && !beneficiary.getActivationStart().equals("")) {
            //     stmt.setDate(12, new java.sql.Date(beneficiary.getActivationStart().getTime()));
            // } else {
            //     stmt.setNull(12, java.sql.Types.DATE);
            // }

            // Check if activationEnd is an empty string or null
            // if (beneficiary.getActivationEnd() != null && !beneficiary.getActivationEnd().equals("")) {
            //     stmt.setDate(13, new java.sql.Date(beneficiary.getActivationEnd().getTime()));
            // } else {
            //     stmt.setNull(13, java.sql.Types.DATE);
            // }
                stmt.execute();
            }
            return null;
        });
    }

    

    public void updateBeneficiary(BeneficiaryDTO beneficiary) throws SQLException {
        jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement stmt = conn.prepareCall("{CALL SP_updateBeneficiary(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
                stmt.setInt(1, beneficiary.getBeneficiaryId());
                stmt.setInt(2, beneficiary.getUserId());
                stmt.setString(3, beneficiary.getName());
                stmt.setString(4, beneficiary.getAccountNumber());
                stmt.setString(5, beneficiary.getBankName());
                stmt.setString(6, beneficiary.getIfscCode());
                stmt.setInt(7, beneficiary.getBeneficiaryType()); 
                stmt.setString(8, beneficiary.getNickname());
                stmt.setString(9, beneficiary.getAddress());
                stmt.setString(10, beneficiary.getMobileNumber());
                stmt.setString(11, beneficiary.getEmail());
                stmt.setDouble(12, beneficiary.getTransferLimit());
                stmt.setDate(13, beneficiary.getActivationStart());
                stmt.setDate(14, beneficiary.getActivationEnd());
             

            // Check if activationStart is an empty string or null
            // if (beneficiary.getActivationStart() != null && !beneficiary.getActivationStart().equals("")) {
            //     stmt.setDate(13, new java.sql.Date(beneficiary.getActivationStart().getTime()));
            // } else {
            //     stmt.setNull(13, java.sql.Types.DATE);
            // }

            // Check if activationEnd is an empty string or null
            // if (beneficiary.getActivationEnd() != null && !beneficiary.getActivationEnd().equals("")) {
            //     stmt.setDate(14, new java.sql.Date(beneficiary.getActivationEnd().getTime()));
            // } else {
            //     stmt.setNull(14, java.sql.Types.DATE);
            // }
                stmt.execute();
            }
            return null;
        });
    }


     // Fetch beneficiaries (either all or by ID depending on queryType)
     public List<BeneficiaryFetchDTO> getBeneficiaries(int queryType, int userId, Integer beneficiaryId) throws SQLException {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement stmt = conn.prepareCall("{CALL SP_getBeneficiaries(?, ?, ?)}")) {
                stmt.setInt(1, queryType);
                stmt.setInt(2, userId);

                if (beneficiaryId != null) {
                    stmt.setInt(3, beneficiaryId);
                } else {
                    stmt.setNull(3, Types.INTEGER);
                }

                ResultSet rs = stmt.executeQuery();
                List<BeneficiaryFetchDTO> beneficiaries = new ArrayList<>();

                while (rs.next()) {
                    BeneficiaryFetchDTO beneficiary = new BeneficiaryFetchDTO();
                    beneficiary.setBeneficiaryId(rs.getInt("beneficiaryId"));
                    beneficiary.setUserId(rs.getInt("userId"));
                    beneficiary.setName(rs.getString("name"));
                    beneficiary.setAccountNumber(rs.getString("accountNumber"));
                    beneficiary.setBankName(rs.getString("bankName"));
                    beneficiary.setIfscCode(rs.getString("ifscCode"));
                    beneficiary.setBeneficiaryType(rs.getInt("beneficiaryType"));
                    beneficiary.setBeneficiaryTypeName(rs.getString("beneficiaryTypeName"));
                    beneficiary.setNickname(rs.getString("nickname"));
                    beneficiary.setAddress(rs.getString("address"));
                    beneficiary.setMobileNumber(rs.getString("mobileNumber"));
                    beneficiary.setEmail(rs.getString("email"));
                    beneficiary.setTransferLimit(rs.getDouble("transferLimit"));
                    beneficiary.setActivationStart(rs.getDate("activationStart"));
                    beneficiary.setActivationEnd(rs.getDate("activationEnd"));

                    beneficiaries.add(beneficiary);
                }

                return beneficiaries;
            }
        });
    }


    public List<BeneficiaryTypeDTO> getAllBeneficiaryTypes() throws SQLException {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement stmt = conn.prepareCall("{CALL SP_getAllBeneficiaryTypes()}")) {
                ResultSet rs = stmt.executeQuery();
                List<BeneficiaryTypeDTO> beneficiaryTypes = new ArrayList<>();
                while (rs.next()) {
                    BeneficiaryTypeDTO type = new BeneficiaryTypeDTO();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    beneficiaryTypes.add(type);
                }
                return beneficiaryTypes;
            }
        });
    };


    public boolean softDeleteBeneficiary(int beneficiaryId) {
        String sql = "CALL SP_SoftDeleteBeneficiary(?)";
        int affectedRows = jdbcTemplate.update(sql, beneficiaryId);

        // If affectedRows > 0, it means the deletion was successful
        return affectedRows > 0;
    }
}
