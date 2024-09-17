package com.ibank.demo.dto;

import java.sql.Date;

public class BeneficiaryFetchDTO {
    private int beneficiaryId;
    private int userId;
    private String name;
    private String accountNumber;
    private String bankName;
    private String ifscCode;
    private int beneficiaryType;
    private String beneficiaryTypeName;
    public String getBeneficiaryTypeName() {
        return beneficiaryTypeName;
    }
    public void setBeneficiaryTypeName(String beneficiaryTypeName) {
        this.beneficiaryTypeName = beneficiaryTypeName;
    }
    private String nickname;
    private String address;
    private String mobileNumber;
    private String email;
    private double transferLimit;
    private Date activationStart;
    private Date activationEnd;
    public int getBeneficiaryId() {
        return beneficiaryId;
    }
    public void setBeneficiaryId(int beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getIfscCode() {
        return ifscCode;
    }
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
    public int getBeneficiaryType() {
        return beneficiaryType;
    }
    public void setBeneficiaryType(int beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public double getTransferLimit() {
        return transferLimit;
    }
    public void setTransferLimit(double transferLimit) {
        this.transferLimit = transferLimit;
    }
    public Date getActivationStart() {
        return activationStart;
    }
    public void setActivationStart(Date activationStart) {
        this.activationStart = activationStart;
    }
    public Date getActivationEnd() {
        return activationEnd;
    }
    public void setActivationEnd(Date activationEnd) {
        this.activationEnd = activationEnd;
    }
    
}

