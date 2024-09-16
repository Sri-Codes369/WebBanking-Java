package com.ibank.demo.dto;

public class AccountStatusDTO {
    private int accStatusId;
    private String accStatus;
    public int getAccStatusId() {
        return accStatusId;
    }
    public AccountStatusDTO(int accStatusId, String accStatus) {
        this.accStatusId = accStatusId;
        this.accStatus = accStatus;
    }
    public void setAccStatusId(int accStatusId) {
        this.accStatusId = accStatusId;
    }
    public String getAccStatus() {
        return accStatus;
    }
    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }
}
