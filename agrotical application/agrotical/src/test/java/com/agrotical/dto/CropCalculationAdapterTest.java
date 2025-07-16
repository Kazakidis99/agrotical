package com.agrotical.dto;

import com.agrotical.entity.Crop;
import com.agrotical.entity.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CropCalculationAdapterTest {

    @Test
    void testToEntity_shouldMapFieldsCorrectly() {
        CropCalculationRequest request = new CropCalculationRequest();
        request.setCropType("Βαμβάκι");

        Field field = new Field();
        field.setId(1L);
        field.setName("Χωράφι 1");

        Crop crop = CropCalculationAdapter.toEntity(request, field);

        assertNotNull(crop);
        assertEquals("Βαμβάκι", crop.getType());
        assertEquals(field, crop.getField());
    }

    @Test
    void testToDto_shouldMapTypeCorrectly() {
        Crop crop = new Crop();
        crop.setType("Καλαμπόκι");

        CropCalculationRequest dto = CropCalculationAdapter.toDto(crop);

        assertNotNull(dto);
        assertEquals("Καλαμπόκι", dto.getCropType());
    }
}
