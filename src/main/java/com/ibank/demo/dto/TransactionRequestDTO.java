package com.ibank.demo.dto;

import java.math.BigDecimal;

public class TransactionRequestDTO {
private int senderAccountId;
    private String recipientAccountNumber;
    private String recipientIFSC;
    private BigDecimal amount;

    // Getters and Setters

    public int getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public String getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public void setRecipientAccountNumber(String recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
    }

    public String getRecipientIFSC() {
        return recipientIFSC;
    }

    public void setRecipientIFSC(String recipientIFSC) {
        this.recipientIFSC = recipientIFSC;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
