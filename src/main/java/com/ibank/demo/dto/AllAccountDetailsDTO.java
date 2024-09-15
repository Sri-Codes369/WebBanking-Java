package com.ibank.demo.dto;

public class AllAccountDetailsDTO {
 // Account information
 private int accountID;
 private String accountNumber;
 private String ifsc;
 private String accFullName;
 private String accEmail;
 private String accPhone;
 private String accAddress;
 private String accCreatedAt;
 private boolean activeStatus;
 private double balanceAmount;
 private String accRemarks;

 // Account status (foreign key)
 private int accountStatusID;
 private String accountStatusName;

 // Account type (foreign key)
 private int accountTypeID;
 private String accountTypeName;

 // User information
 private int userID;
 private String userName;
 private String userFirstName;
 private String userLastName;
 private String userEmail;
 private String userPhone;
 private String userAddress;
 private String userCreatedAt;

 // KYC details
 private int kycID;
 private int kycOptionID;
 private String kycOptionLabel;
 private String kycDocumentNumber;
 private int kycStatusID;
 private String kycStatusName;
public int getAccountID() {
    return accountID;
}
public void setAccountID(int accountID) {
    this.accountID = accountID;
}
public String getAccountNumber() {
    return accountNumber;
}
public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
}
public String getIfsc() {
    return ifsc;
}
public void setIfsc(String ifsc) {
    this.ifsc = ifsc;
}
public String getAccFullName() {
    return accFullName;
}
public void setAccFullName(String accFullName) {
    this.accFullName = accFullName;
}
public String getAccEmail() {
    return accEmail;
}
public void setAccEmail(String accEmail) {
    this.accEmail = accEmail;
}
public String getAccPhone() {
    return accPhone;
}
public void setAccPhone(String accPhone) {
    this.accPhone = accPhone;
}
public String getAccAddress() {
    return accAddress;
}
public void setAccAddress(String accAddress) {
    this.accAddress = accAddress;
}
public String getAccCreatedAt() {
    return accCreatedAt;
}
public void setAccCreatedAt(String accCreatedAt) {
    this.accCreatedAt = accCreatedAt;
}
public boolean isActiveStatus() {
    return activeStatus;
}
public void setActiveStatus(boolean activeStatus) {
    this.activeStatus = activeStatus;
}
public double getBalanceAmount() {
    return balanceAmount;
}
public void setBalanceAmount(double balanceAmount) {
    this.balanceAmount = balanceAmount;
}
public String getAccRemarks() {
    return accRemarks;
}
public void setAccRemarks(String accRemarks) {
    this.accRemarks = accRemarks;
}
public int getAccountStatusID() {
    return accountStatusID;
}
public void setAccountStatusID(int accountStatusID) {
    this.accountStatusID = accountStatusID;
}
public String getAccountStatusName() {
    return accountStatusName;
}
public void setAccountStatusName(String accountStatusName) {
    this.accountStatusName = accountStatusName;
}
public int getAccountTypeID() {
    return accountTypeID;
}
public void setAccountTypeID(int accountTypeID) {
    this.accountTypeID = accountTypeID;
}
public String getAccountTypeName() {
    return accountTypeName;
}
public void setAccountTypeName(String accountTypeName) {
    this.accountTypeName = accountTypeName;
}
public int getUserID() {
    return userID;
}
public void setUserID(int userID) {
    this.userID = userID;
}
public String getUserName() {
    return userName;
}
public void setUserName(String userName) {
    this.userName = userName;
}
public String getUserFirstName() {
    return userFirstName;
}
public void setUserFirstName(String userFirstName) {
    this.userFirstName = userFirstName;
}
public String getUserLastName() {
    return userLastName;
}
public void setUserLastName(String userLastName) {
    this.userLastName = userLastName;
}
public String getUserEmail() {
    return userEmail;
}
public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
}
public String getUserPhone() {
    return userPhone;
}
public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
}
public String getUserAddress() {
    return userAddress;
}
public void setUserAddress(String userAddress) {
    this.userAddress = userAddress;
}
public String getUserCreatedAt() {
    return userCreatedAt;
}
public void setUserCreatedAt(String userCreatedAt) {
    this.userCreatedAt = userCreatedAt;
}
public int getKycID() {
    return kycID;
}
public void setKycID(int kycID) {
    this.kycID = kycID;
}
public int getKycOptionID() {
    return kycOptionID;
}
public void setKycOptionID(int kycOptionID) {
    this.kycOptionID = kycOptionID;
}
public String getKycOptionLabel() {
    return kycOptionLabel;
}
public void setKycOptionLabel(String kycOptionLabel) {
    this.kycOptionLabel = kycOptionLabel;
}
public String getKycDocumentNumber() {
    return kycDocumentNumber;
}
public void setKycDocumentNumber(String kycDocumentNumber) {
    this.kycDocumentNumber = kycDocumentNumber;
}
public int getKycStatusID() {
    return kycStatusID;
}
public void setKycStatusID(int kycStatusID) {
    this.kycStatusID = kycStatusID;
}
public String getKycStatusName() {
    return kycStatusName;
}
public void setKycStatusName(String kycStatusName) {
    this.kycStatusName = kycStatusName;
}
}
