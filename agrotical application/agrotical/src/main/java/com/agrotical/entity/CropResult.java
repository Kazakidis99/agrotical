package com.agrotical.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "crop_results")
public class CropResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cropType;
    private double areaInStremma;
    private boolean fertilized;
    private boolean sprayed;
    private boolean irrigated;
    private double productionKg;
    private double revenue;
    private double expenses;
    private double profit;

    private int productionPerStremma;
    private int productionTotal;
    private int expensesPerStremma;
    private int totalExpenses;
    private int revenuePerStremma;
    private int totalRevenue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = true)
    private Field field;

    public CropResult() {}

    private CropResult(Builder builder) {
        this.cropType = builder.cropType;
        this.areaInStremma = builder.areaInStremma;
        this.fertilized = builder.fertilized;
        this.sprayed = builder.sprayed;
        this.irrigated = builder.irrigated;
        this.productionKg = builder.productionKg;
        this.revenue = builder.revenue;
        this.expenses = builder.expenses;
        this.profit = builder.profit;
        this.productionPerStremma = builder.productionPerStremma;
        this.productionTotal = builder.productionTotal;
        this.expensesPerStremma = builder.expensesPerStremma;
        this.totalExpenses = builder.totalExpenses;
        this.revenuePerStremma = builder.revenuePerStremma;
        this.totalRevenue = builder.totalRevenue;
        this.field = builder.field;
    }

    public Long getId() { return id; }
    public String getCropType() { return cropType; }
    public void setCropType(String cropType) { this.cropType = cropType; }
    public double getAreaInStremma() { return areaInStremma; }
    public void setAreaInStremma(double areaInStremma) { this.areaInStremma = areaInStremma; }
    public boolean isFertilized() { return fertilized; }
    public void setFertilized(boolean fertilized) { this.fertilized = fertilized; }
    public boolean isSprayed() { return sprayed; }
    public void setSprayed(boolean sprayed) { this.sprayed = sprayed; }
    public boolean isIrrigated() { return irrigated; }
    public void setIrrigated(boolean irrigated) { this.irrigated = irrigated; }
    public double getProductionKg() { return productionKg; }
    public void setProductionKg(double productionKg) { this.productionKg = productionKg; }
    public double getRevenue() { return revenue; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
    public double getExpenses() { return expenses; }
    public void setExpenses(double expenses) { this.expenses = expenses; }
    public double getProfit() { return profit; }
    public void setProfit(double profit) { this.profit = profit; }
    public Field getField() { return field; }
    public void setField(Field field) { this.field = field; }

    public int getProductionPerStremma() { return productionPerStremma; }
    public void setProductionPerStremma(int productionPerStremma) { this.productionPerStremma = productionPerStremma; }

    public int getProductionTotal() { return productionTotal; }
    public void setProductionTotal(int productionTotal) { this.productionTotal = productionTotal; }

    public int getExpensesPerStremma() { return expensesPerStremma; }
    public void setExpensesPerStremma(int expensesPerStremma) { this.expensesPerStremma = expensesPerStremma; }

    public int getTotalExpenses() { return totalExpenses; }
    public void setTotalExpenses(int totalExpenses) { this.totalExpenses = totalExpenses; }

    public int getRevenuePerStremma() { return revenuePerStremma; }
    public void setRevenuePerStremma(int revenuePerStremma) { this.revenuePerStremma = revenuePerStremma; }

    public int getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(int totalRevenue) { this.totalRevenue = totalRevenue; }

    public static class Builder {
        private String cropType;
        private double areaInStremma;
        private boolean fertilized;
        private boolean sprayed;
        private boolean irrigated;
        private double productionKg;
        private double revenue;
        private double expenses;
        private double profit;

        private int productionPerStremma;
        private int productionTotal;
        private int expensesPerStremma;
        private int totalExpenses;
        private int revenuePerStremma;
        private int totalRevenue;

        private Field field;

        public Builder cropType(String cropType) { this.cropType = cropType; return this; }
        public Builder areaInStremma(double area) { this.areaInStremma = area; return this; }
        public Builder fertilized(boolean fertilized) { this.fertilized = fertilized; return this; }
        public Builder sprayed(boolean sprayed) { this.sprayed = sprayed; return this; }
        public Builder irrigated(boolean irrigated) { this.irrigated = irrigated; return this; }
        public Builder productionKg(double kg) { this.productionKg = kg; return this; }
        public Builder revenue(double revenue) { this.revenue = revenue; return this; }
        public Builder expenses(double expenses) { this.expenses = expenses; return this; }
        public Builder profit(double profit) { this.profit = profit; return this; }

        public Builder productionPerStremma(int val) { this.productionPerStremma = val; return this; }
        public Builder productionTotal(int val) { this.productionTotal = val; return this; }
        public Builder expensesPerStremma(int val) { this.expensesPerStremma = val; return this; }
        public Builder totalExpenses(int val) { this.totalExpenses = val; return this; }
        public Builder revenuePerStremma(int val) { this.revenuePerStremma = val; return this; }
        public Builder totalRevenue(int val) { this.totalRevenue = val; return this; }

        public Builder field(Field field) { this.field = field; return this; }

        public CropResult build() { return new CropResult(this); }
    }
}
