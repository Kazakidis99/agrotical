package com.agrotical.service;

import com.agrotical.entity.Crop;
import com.agrotical.entity.Field;

public class CropFactory {

    
    public static Crop create(String cropType, Field field) {
        return new Crop.Builder()
                .type(cropType)
                .field(field)
                .build();
    }

    public static Crop create(String cropType, String name, Field field) {
        return new Crop.Builder()
                .type(cropType)
                .name(name)
                .field(field)
                .build();
    }
} 
