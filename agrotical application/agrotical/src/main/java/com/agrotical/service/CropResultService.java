package com.agrotical.service;

import com.agrotical.entity.CropResult;
import com.agrotical.entity.Field;
import com.agrotical.repository.CropResultRepository;
import com.agrotical.dto.CropResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CropResultService {

    private final CropResultRepository cropResultRepository;

    @Autowired
    public CropResultService(CropResultRepository cropResultRepository) {
        this.cropResultRepository = cropResultRepository;
    }

    @Transactional
    public void deleteById(Long id) {
        CropResult result = cropResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Δεν βρέθηκε"));
        result.setField(null);
        cropResultRepository.delete(result);
    }

    public CropResult calculateAndSave(Field field, String cropType, double area,
                                       boolean fertilized, boolean sprayed, boolean irrigated) {

        CropResult result = new CropResult();
        result.setField(field);
        result.setCropType(cropType);
        result.setAreaInStremma((int) Math.round(area));
        result.setFertilized(fertilized);
        result.setSprayed(sprayed);
        result.setIrrigated(irrigated);

        calculateResultFields(result);

        return cropResultRepository.save(result);
    }

    public CropResult updateCropResult(Long id, CropResult updatedResult) {
        CropResult existing = cropResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CropResult not found with id: " + id));

        existing.setAreaInStremma(updatedResult.getAreaInStremma());
        existing.setFertilized(updatedResult.isFertilized());
        existing.setSprayed(updatedResult.isSprayed());
        existing.setIrrigated(updatedResult.isIrrigated());
        existing.setCropType(updatedResult.getCropType());

        if (updatedResult.getField() != null) {
            existing.setField(updatedResult.getField());
        }

        calculateResultFields(existing);

        return cropResultRepository.save(existing);
    }

    private void calculateResultFields(CropResult result) {
        String cropType = result.getCropType().toLowerCase();
        int area = (int) result.getAreaInStremma();

        int seedKgPerStremma = 15;
        int fertilizerCost = 23;
        int pesticideCost = 12;

        int seedCostPerSack = 0;
        double sellPricePerKg = 0;
        int yieldFactor = 0;

        switch (cropType) {
            case "σιτάρι" -> {
                seedCostPerSack = 20;
                sellPricePerKg = 0.38;
                yieldFactor = 17;
            }
            case "βαμβάκι" -> {
                seedCostPerSack = 105;
                sellPricePerKg = 0.59;
                yieldFactor = 19;
            }
            case "ηλιόσπορος" -> {
                seedCostPerSack = 90;
                sellPricePerKg = 0.47;
                yieldFactor = 18;
            }
        }

        int totalSeedKg = seedKgPerStremma * area;
        int sacks = (int) Math.ceil(totalSeedKg / 20.0);
        int seedCost = sacks * seedCostPerSack;

        double modifiedYield = seedKgPerStremma * yieldFactor;

        if (result.isFertilized()) modifiedYield *= 1.35;
        if (result.isSprayed()) modifiedYield *= 1.16;
        if (!result.isIrrigated()) modifiedYield *= 0.85;

        int productionPerStremma = (int) Math.round(modifiedYield);
        int totalProduction = productionPerStremma * area;

        int fertCost = result.isFertilized() ? (int) Math.round(area * fertilizerCost) : 0;
        int pestCost = result.isSprayed() ? (int) Math.round(area * pesticideCost) : 0;
        int totalExpenses = seedCost + fertCost + pestCost;
        int expensesPerStremma = totalExpenses / area;

        int revenuePerStremma = (int) Math.round(productionPerStremma * sellPricePerKg);
        int totalRevenue = revenuePerStremma * area;

        int profit = totalRevenue - totalExpenses;

        result.setProductionKg(totalProduction);
        result.setProductionPerStremma(productionPerStremma);
        result.setProductionTotal(totalProduction);
        result.setExpenses(totalExpenses);
        result.setExpensesPerStremma(expensesPerStremma);
        result.setTotalExpenses(totalExpenses);
        result.setRevenue(totalRevenue);
        result.setRevenuePerStremma(revenuePerStremma);
        result.setTotalRevenue(totalRevenue);
        result.setProfit(profit);
    }

    public List<CropResult> getResultsByField(Field field) {
        return cropResultRepository.findByField(field);
    }

    public List<CropResult> getResultsByUserId(Long userId) {
        return cropResultRepository.findByField_User_Id(userId);
    }

    public List<CropResult> getResultsByFieldId(Long fieldId) {
        return cropResultRepository.findByField_Id(fieldId);
    }

    public List<CropResultResponse> getCropResultsForUser(Long userId) {
        List<CropResult> results = cropResultRepository.findByField_User_Id(userId);
        return results.stream()
                .map(CropResultResponse::new)
                .collect(Collectors.toList());
    }

    
}
