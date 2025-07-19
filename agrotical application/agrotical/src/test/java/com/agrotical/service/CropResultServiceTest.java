package com.agrotical.service;


import com.agrotical.entity.CropResult;
import com.agrotical.entity.Field;
import com.agrotical.entity.User;
import com.agrotical.repository.CropResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CropResultServiceTest {

    private CropResultService cropResultService;
    private CropResultRepository cropResultRepository;

    @BeforeEach
    void setUp() {
        cropResultRepository = mock(CropResultRepository.class);
        cropResultService = new CropResultService(cropResultRepository);
    }

    @Test
    void summaryForUser() {
        Long userId = 1L;

        
        User mockUser = new User();
        mockUser.setId(userId);

        Field mockField = new Field();
        mockField.setId(10L);
        mockField.setUser(mockUser);

        
        CropResult result1 = new CropResult();
        result1.setField(mockField);
        result1.setAreaInStremma(10);
        result1.setProductionKg(500);
        result1.setExpenses(200);
        result1.setRevenue(800);
        result1.setProfit(600);

        CropResult result2 = new CropResult();
        result2.setField(mockField);
        result2.setAreaInStremma(5);
        result2.setProductionKg(250);
        result2.setExpenses(100);
        result2.setRevenue(400);
        result2.setProfit(300);

        
        when(cropResultRepository.findByField_User_Id(userId))
            .thenReturn(List.of(result1, result2));

        


        
      
    }
}
