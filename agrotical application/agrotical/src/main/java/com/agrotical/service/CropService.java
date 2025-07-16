package com.agrotical.service;

import com.agrotical.entity.Crop;
import com.agrotical.repository.CropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CropService {

    @Autowired
    private CropRepository cropRepository;

    public CropService(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    public Crop createCrop(Crop crop) {
        return cropRepository.save(crop);
    }

    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

    public Optional<Crop> getCropById(Long id) {
        return cropRepository.findById(id);
    }

    public void deleteCrop(Long id) {
        cropRepository.deleteById(id);
    }

    public Optional<Crop> updateCrop(Long id, Crop newCropData) {
        return cropRepository.findById(id).map(existingCrop -> {
            existingCrop.setName(newCropData.getName());
            existingCrop.setType(newCropData.getType());
            return cropRepository.save(existingCrop);
        });
    }
}
