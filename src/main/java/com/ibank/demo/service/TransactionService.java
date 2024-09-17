package com.ibank.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.ibank.demo.dto.TransactionDetailsDTO;


import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> createTransaction(int senderAccountId, String recipientAccountNumber, String recipientIFSC, BigDecimal amount) {
        Map<String, Object> result = new HashMap<>();
        int statusCode;
        String message;

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_CreateTransaction")
                .declareParameters(
                    new SqlOutParameter("p_statusCode", Types.INTEGER),
                    new SqlOutParameter("p_message", Types.VARCHAR)
                );

            Map<String, Object> inParams = new HashMap<>();
            inParams.put("p_senderAccountId", senderAccountId);
            inParams.put("p_recipientAccountNumber", recipientAccountNumber);
            inParams.put("p_recipientIFSC", recipientIFSC);
            inParams.put("p_amount", amount);

            Map<String, Object> outParams = jdbcCall.execute(inParams);
            statusCode = (Integer) outParams.get("p_statusCode");
            message = (String) outParams.get("p_message");

            result.put("statusCode", statusCode);
            result.put("message", message);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("statusCode", 4); // General error code
            result.put("message", "Transaction failed");
        }

        return result;
    };


    public List<TransactionDetailsDTO> getTransactionDetails(int queryType, Integer userId) throws SQLException {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement stmt = conn.prepareCall("{CALL SP_GetTransactionDetails(?, ?)}")) {
                // Set the input parameters for the stored procedure
                stmt.setInt(1, queryType);
                if (queryType == 2 && userId != null) {
                    stmt.setInt(2, userId);
                } else {
                    stmt.setNull(2, java.sql.Types.INTEGER);
                }
    
                // Execute the stored procedure and process the result set
                ResultSet rs = stmt.executeQuery();
                List<TransactionDetailsDTO> transactions = new ArrayList<>();
    
                while (rs.next()) {
                    TransactionDetailsDTO dto = new TransactionDetailsDTO();
                    dto.setTransactionId(rs.getInt("transactionId"));
                    dto.setSenderAccountId(rs.getInt("senderAccountId"));
                    dto.setSenderName(rs.getString("senderName"));
                    dto.setRecipientName(rs.getString("recipientName"));
                    dto.setAmount(rs.getDouble("amount"));
                    dto.setTransactionDate(rs.getString("transactionDate"));
                    dto.setStatus(rs.getString("status"));
                    dto.setTransactionType(rs.getString("transactionType"));
                    dto.setReferenceNumber(rs.getString("referenceNumber"));
                    transactions.add(dto);
                }
                return transactions;
            }
        });
    }
    
}
