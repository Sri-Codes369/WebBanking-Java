package com.ibank.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ibank.demo.dto.AccountStatusDTO;
import com.ibank.demo.dto.AccountTypeDTO;
import com.ibank.demo.dto.KYCOptionDTO;

import jakarta.transaction.Transactional;

@Service
public class LookUpService {

     @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public List<AccountTypeDTO> getAllAccountTypes() {
        String sql = "CALL GetAccountTypes()";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new AccountTypeDTO(
                rs.getInt("AccountTypeID"),
                rs.getString("AccountTypeLabel")
        ));
    }
    
    @Transactional
    public List<KYCOptionDTO> getAllKYCOptions() {
        String sql = "CALL GetKYCOptions()";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new KYCOptionDTO(
                rs.getInt("KYCOptionID"),
                rs.getString("KYCOptionLabel")
        ));
    }

    @Transactional
    public List<AccountStatusDTO> getACCStatus() {
        String sql = "CALL SP_GetAccoutStatusOptions()";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new AccountStatusDTO(
                rs.getInt("StatusId"),
                rs.getString("Status")
        ));
    }


}
