package com.agrotical.dto;

import com.agrotical.entity.Crop;
import com.agrotical.entity.Field;

public class CropCalculationAdapter {

    
    public static Crop toEntity(CropCalculationRequest request, Field field) {
        return new Crop.Builder()
                .type(request.getCropType())
                .field(field)
                .build();
    }

    
    public static CropCalculationRequest toDto(Crop crop) {
        CropCalculationRequest dto = new CropCalculationRequest();
        dto.setCropType(crop.getType());
        return dto;
    }
} 
