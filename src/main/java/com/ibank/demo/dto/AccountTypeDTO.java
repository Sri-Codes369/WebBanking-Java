package com.ibank.demo.dto;

public class AccountTypeDTO {
    private Integer accountTypeID;
    private String accountTypeLabel;

    public AccountTypeDTO(Integer accountTypeID, String accountTypeLabel) {
        this.accountTypeID = accountTypeID;
        this.accountTypeLabel = accountTypeLabel;
    }

    // Getters and Setters
    public Integer getAccountTypeID() {
        return accountTypeID;
    }

    public void setAccountTypeID(Integer accountTypeID) {
        this.accountTypeID = accountTypeID;
    }


    public String getAccountTypeLabel() {
        return accountTypeLabel;
    }

    public void setAccountTypeLabel(String accountTypeLabel) {
        this.accountTypeLabel = accountTypeLabel;
    }
}
