package com.ibank.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibank.demo.dto.AccCreationDTO;
import com.ibank.demo.dto.AccountDetailsDTO;
import com.ibank.demo.dto.AllAccountDetailsDTO;
import com.ibank.demo.dto.ApiResponseDTO;
import com.ibank.demo.dto.UpdateAccountStatusDTO;

@Service
public class AccountService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

     public Map<String, Object> insertAccountAndKyc(AccCreationDTO dto) {
        String sql = "{call SP_InsertAccountAndKYC(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        return jdbcTemplate.execute((Connection conn) -> {
            Map<String, Object> resultMap = new HashMap<>();
            try (CallableStatement cs = conn.prepareCall(sql)) {
                // Set input parameters
                cs.setInt(1, dto.getUserID());
                cs.setInt(2, dto.getAccountTypeId());
                cs.setString(3, dto.getFullName());
                cs.setString(4, dto.getEmail());
                cs.setString(5, dto.getPhone());
                cs.setString(6, dto.getAddress());
                cs.setInt(7, dto.getKycDocumentTypeId());
                cs.setString(8, dto.getKycDocumentNumber());

                // Register output parameters
                cs.registerOutParameter(9, java.sql.Types.INTEGER);  // Response code
                cs.registerOutParameter(10, java.sql.Types.VARCHAR); // Response message

                // Execute the stored procedure
                cs.execute();

                // Get the output parameters
                int responseCode = cs.getInt(9);
                String responseMessage = cs.getString(10);

                // Prepare result as key-value pairs
                resultMap.put("Code", responseCode);
                resultMap.put("Message", responseMessage);

                return resultMap;

            } catch (SQLException e) {
                throw new RuntimeException("Error executing stored procedure: " + e.getMessage());
            }
        });
    }

    

    @SuppressWarnings("deprecation")
    public List<AccountDetailsDTO> getUserAccountDetails(int userId) {
        String sql = "{call SP_GetUserAccountDetails(?)}";
        return jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(AccountDetailsDTO.class));
    }

    @SuppressWarnings("deprecation")
    public List<AllAccountDetailsDTO> getAllAccountDetails(int queryType, int accountId) {
        String sql = "{call SP_GetAccountDetails(?,?)}";
        return jdbcTemplate.query(sql, new Object[]{queryType, accountId}, new BeanPropertyRowMapper<>(AllAccountDetailsDTO.class));
    }

    public ApiResponseDTO updateAccountStatus(UpdateAccountStatusDTO updateAccountStatusDTO) {
        try {
            // Call the stored procedure
            jdbcTemplate.execute((Connection connection) -> {
                try (CallableStatement callableStatement = connection.prepareCall("{call SP_UpdateAccountStatus(?, ?, ?)}")) {
                    callableStatement.setInt(1, updateAccountStatusDTO.getAccountId());
                    callableStatement.setInt(2, updateAccountStatusDTO.getNewAccountStatus());
                    callableStatement.setString(3, updateAccountStatusDTO.getNewRemarks());

                    // Execute the stored procedure
                    callableStatement.execute();
                }
                return null;
            });

            // Return success response
            return new ApiResponseDTO("Account status updated successfully.", true);

        } catch (Exception e) {
            // Handle error and return failure response
            return new ApiResponseDTO("Failed to update account status: " + e.getMessage(), false);
        }
    }
}
