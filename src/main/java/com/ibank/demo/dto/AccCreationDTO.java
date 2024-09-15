package com.ibank.demo.dto;

public class AccCreationDTO {
    private int userID;
    private int accountTypeId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private int kycDocumentTypeId;
    private String kycDocumentNumber;

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getKycDocumentTypeId() {
        return kycDocumentTypeId;
    }

    public void setKycDocumentTypeId(int kycDocumentTypeId) {
        this.kycDocumentTypeId = kycDocumentTypeId;
    }

    public String getKycDocumentNumber() {
        return kycDocumentNumber;
    }

    public void setKycDocumentNumber(String kycDocumentNumber) {
        this.kycDocumentNumber = kycDocumentNumber;
    }
}
