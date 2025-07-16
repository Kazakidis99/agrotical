package com.agrotical.dto;

import com.agrotical.entity.CropResult;

public class CropResultResponse {
    private Long id;
    private Long fieldId;
    private String fieldName;
    private String cropType;
    private int area;
    private int production;
    private int expenses;
    private int revenue;
    private int profit;

    private int productionPerStremma;
    private int productionTotal;
    private int expensesPerStremma;
    private int totalExpenses;
    private int revenuePerStremma;
    private int totalRevenue;

    public CropResultResponse() {}

    public CropResultResponse(Long id,
                              String cropType,
                              double areaInStremma,
                              int productionPerStremma,
                              int productionTotal,
                              int expensesPerStremma,
                              int totalExpenses,
                              int revenuePerStremma,
                              int totalRevenue,
                              double profit) {
        this.id = id;
        this.cropType = cropType;
        this.area = (int) Math.round(areaInStremma);
        this.productionPerStremma = productionPerStremma;
        this.productionTotal = productionTotal;
        this.expensesPerStremma = expensesPerStremma;
        this.totalExpenses = totalExpenses;
        this.revenuePerStremma = revenuePerStremma;
        this.totalRevenue = totalRevenue;
        this.profit = (int) Math.round(profit);
    }

    public CropResultResponse(CropResult result) {
        this.id = result.getId();
        this.cropType = result.getCropType();
        this.area = (int) Math.round(result.getAreaInStremma());
        this.production = (int) Math.round(result.getProductionKg());
        this.expenses = (int) Math.round(result.getExpenses());
        this.revenue = (int) Math.round(result.getRevenue());
        this.profit = (int) Math.round(result.getProfit());

        this.productionPerStremma = area > 0 ? production / area : 0;
        this.expensesPerStremma = area > 0 ? expenses / area : 0;
        this.revenuePerStremma = area > 0 ? revenue / area : 0;

        this.productionTotal = production;
        this.totalExpenses = expenses;
        this.totalRevenue = revenue;

        if (result.getField() != null) {
            this.fieldName = result.getField().getName();
            this.fieldId = result.getField().getId();
        } else {
            this.fieldName = "Άγνωστο";
            this.fieldId = null;
        }
    }

    public static CropResultResponse fromEntity(CropResult result) {
        return new CropResultResponse(result);
    }

    public Long getId() {
        return id;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getCropType() {
        return cropType;
    }

    public int getArea() {
        return area;
    }

    public int getProduction() {
        return production;
    }

    public int getExpenses() {
        return expenses;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getProfit() {
        return profit;
    }

    public int getProductionPerStremma() {
        return productionPerStremma;
    }

    public int getProductionTotal() {
        return productionTotal;
    }

    public int getExpensesPerStremma() {
        return expensesPerStremma;
    }

    public int getTotalExpenses() {
        return totalExpenses;
    }

    public int getRevenuePerStremma() {
        return revenuePerStremma;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
