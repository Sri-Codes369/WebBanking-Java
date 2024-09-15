package com.ibank.demo.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AccountDetailsDTO {
private int accountID;
    private String IFSC;
    private String accountNumber;
    private int accountTypeId;
    private String accountType;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Timestamp createdAt;
    private boolean activeStatus;
    private BigDecimal balanceAmount;
    private int accountStatusId;
    private String accountStatus;
    private String remarks;

    // Getters and Setters
    public int getAccountID() { return accountID; }
    public void setAccountID(int accountID) { this.accountID = accountID; }

    public String getIFSC() { return IFSC; }
    public void setIFSC(String IFSC) { this.IFSC = IFSC; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public int getAccountTypeId() { return accountTypeId; }
    public void setAccountTypeId(int accountTypeId) { this.accountTypeId = accountTypeId; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public boolean isActiveStatus() { return activeStatus; }
    public void setActiveStatus(boolean activeStatus) { this.activeStatus = activeStatus; }

    public BigDecimal getBalanceAmount() { return balanceAmount; }
    public void setBalanceAmount(BigDecimal balanceAmount) { this.balanceAmount = balanceAmount; }

    public int getAccountStatusId() { return accountStatusId; }
    public void setAccountStatusId(int accountStatusId) { this.accountStatusId = accountStatusId; }

    public String getAccountStatus() { return accountStatus; }
    public void setAccountStatus(String accountStatus) { this.accountStatus = accountStatus; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
