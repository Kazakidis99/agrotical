package com.agrotical.service;

import com.agrotical.entity.Crop;
import com.agrotical.repository.CropRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CropServiceTest {

    private CropService cropService;
    private CropRepository cropRepository;

    @BeforeEach
    void setUp() {
        cropRepository = mock(CropRepository.class);
        cropService = new CropService(cropRepository);
    }

    @Test
    void update_whenExists() {
        Long cropId = 1L;

        
        Crop existingCrop = new Crop();
        existingCrop.setId(cropId);
        existingCrop.setName("Old Name");
        existingCrop.setType("Old Type");

       
        Crop newCropData = new Crop();
        newCropData.setName("New Name");
        newCropData.setType("New Type");

        
        when(cropRepository.findById(cropId)).thenReturn(Optional.of(existingCrop));
        when(cropRepository.save(any(Crop.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Crop> updatedCropOpt = cropService.updateCrop(cropId, newCropData);

        assertTrue(updatedCropOpt.isPresent());
        Crop updatedCrop = updatedCropOpt.get();
        assertEquals("New Name", updatedCrop.getName());
        assertEquals("New Type", updatedCrop.getType());

        verify(cropRepository).findById(cropId);
        verify(cropRepository).save(existingCrop);
    }

    @Test
    void update_whenMissing() {
        Long cropId = 2L;
        Crop newCropData = new Crop();
        newCropData.setName("New");
        newCropData.setType("Type");

        when(cropRepository.findById(cropId)).thenReturn(Optional.empty());

        Optional<Crop> result = cropService.updateCrop(cropId, newCropData);

        assertTrue(result.isEmpty());
        verify(cropRepository).findById(cropId);
        verify(cropRepository, never()).save(any());
    }
}
