package com.ibank.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
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
    }
}
