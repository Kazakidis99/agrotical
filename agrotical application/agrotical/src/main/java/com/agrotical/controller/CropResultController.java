package com.agrotical.controller;

import com.agrotical.dto.CropCalculationRequest;
import com.agrotical.dto.CropResultResponse;
import com.agrotical.entity.CropResult;
import com.agrotical.entity.Field;
import com.agrotical.service.CropResultService;
import com.agrotical.service.FieldService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/crop-results")
public class CropResultController {

    private final CropResultService cropResultService;
    private final FieldService fieldService;

    public CropResultController(CropResultService cropResultService, FieldService fieldService) {
        this.cropResultService = cropResultService;
        this.fieldService = fieldService;
    }

    
    @GetMapping("/user/{userId}")
    public List<CropResultResponse> getResultsByUserId(@PathVariable Long userId) {
        return cropResultService.getCropResultsForUser(userId);
    }

    @GetMapping("/field/{fieldId}")
    public List<CropResultResponse> getResultsByFieldId(@PathVariable Long fieldId) {
        return cropResultService.getResultsByFieldId(fieldId).stream()
            .map(CropResultResponse::new)
            .toList();
    }

    @PostMapping("/calculate")
        public CropResultResponse calculateCropResult(@RequestBody CropCalculationRequest request) {
        Field field = fieldService.getFieldById(request.getFieldId())
        .orElseThrow(() -> new RuntimeException("Field not found with id: " + request.getFieldId()));

        CropResult result = cropResultService.calculateAndSave(
        field,
        request.getCropType(),
        request.getAreaInStremma(),
        request.isFertilized(),
        request.isSprayed(),
        request.isIrrigated()
    );

    return new CropResultResponse(
        result.getId(),
        result.getCropType(),
        result.getAreaInStremma(),
        result.getProductionPerStremma(),
        result.getProductionTotal(),
        result.getExpensesPerStremma(),
        result.getTotalExpenses(),
        result.getRevenuePerStremma(),
        result.getTotalRevenue(),
        result.getProfit()
    );
}


   

    
    @DeleteMapping("/{resultId}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long resultId) {
        cropResultService.deleteById(resultId);
        return ResponseEntity.noContent().build();
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<CropResultResponse> updateCropResult(@PathVariable Long id, @RequestBody CropResult updatedResult) {
        CropResult updated = cropResultService.updateCropResult(id, updatedResult);
        return ResponseEntity.ok(CropResultResponse.fromEntity(updated));
    }

}
