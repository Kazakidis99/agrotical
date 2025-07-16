package com.agrotical.controller;

import com.agrotical.entity.Crop;
import com.agrotical.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.agrotical.dto.CropCalculationRequest;



import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/crops")
public class CropController {

    @Autowired
    private CropService cropService;

    

    @PostMapping
    public ResponseEntity<Crop> createCrop(@RequestBody Crop crop) {
        Crop saved = cropService.createCrop(crop);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Crop>> getAllCrops() {
        return ResponseEntity.ok(cropService.getAllCrops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crop> getCropById(@PathVariable Long id) {
        return cropService.getCropById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        cropService.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crop> updateCrop(@PathVariable Long id, @RequestBody Crop crop) {
        return cropService.updateCrop(id, crop)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    


}
