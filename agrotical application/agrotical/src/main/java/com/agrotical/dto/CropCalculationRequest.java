package com.agrotical.dto;

public class CropCalculationRequest {
    private String cropType;
    private int areaInStremma;
    private boolean fertilized;
    private boolean sprayed;
    private boolean irrigated;
    private Long fieldId;

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public int getAreaInStremma() {
        return areaInStremma;
    }

    public void setAreaInStremma(int areaInStremma) {
        this.areaInStremma = areaInStremma;
    }

    public boolean isFertilized() {
        return fertilized;
    }

    public void setFertilized(boolean fertilized) {
        this.fertilized = fertilized;
    }

    public boolean isSprayed() {
        return sprayed;
    }

    public void setSprayed(boolean sprayed) {
        this.sprayed = sprayed;
    }

    public boolean isIrrigated() {
        return irrigated;
    }

    public void setIrrigated(boolean irrigated) {
        this.irrigated = irrigated;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }
}
