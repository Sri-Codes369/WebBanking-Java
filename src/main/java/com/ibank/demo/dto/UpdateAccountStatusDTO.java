package com.ibank.demo.dto;

public class UpdateAccountStatusDTO {
    private int accountId;
    private int newAccountStatus;
    private String newRemarks;

    // Constructors
    public UpdateAccountStatusDTO() {}

    public UpdateAccountStatusDTO(int accountId, int newAccountStatus, String newRemarks) {
        this.accountId = accountId;
        this.newAccountStatus = newAccountStatus;
        this.newRemarks = newRemarks;
    }

    // Getters and Setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getNewAccountStatus() {
        return newAccountStatus;
    }

    public void setNewAccountStatus(int newAccountStatus) {
        this.newAccountStatus = newAccountStatus;
    }

    public String getNewRemarks() {
        return newRemarks;
    }

    public void setNewRemarks(String newRemarks) {
        this.newRemarks = newRemarks;
    }
}
