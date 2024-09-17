package com.ibank.demo.dto;

public class KYCOptionDTO {
    private int kycOptionID;
    private String kycOptionLabel;


    public KYCOptionDTO(Integer kycOptionID, String kycOptionLabel) {
        this.kycOptionID = kycOptionID;
        this.kycOptionLabel = kycOptionLabel;
    }

    // Getters and Setters
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
}
