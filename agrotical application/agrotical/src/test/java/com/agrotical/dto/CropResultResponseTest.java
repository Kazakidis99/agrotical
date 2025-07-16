package com.agrotical.dto;

import com.agrotical.entity.CropResult;
import com.agrotical.entity.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CropResultResponseTest {

    @Test
    void testConstructor_withValidField_shouldCalculateCorrectly() {
        Field field = new Field();
        field.setId(10L);
        field.setName("Χωράφι 1");

        CropResult result = new CropResult();
        result.setField(field);
        result.setCropType("Σιτάρι");
        result.setAreaInStremma(10.0);         
        result.setProductionKg(1000.0);        
        result.setExpenses(500.0);
        result.setRevenue(1500.0);
        result.setProfit(1000.0);

        CropResultResponse response = new CropResultResponse(result);

        assertEquals("Σιτάρι", response.getCropType());
        assertEquals(10, response.getArea());
        assertEquals(1000, response.getProduction());
        assertEquals(500, response.getExpenses());
        assertEquals(1500, response.getRevenue());
        assertEquals(1000, response.getProfit());
        assertEquals(100, response.getProductionPerStremma());
        assertEquals(50, response.getExpensesPerStremma());
        assertEquals(150, response.getRevenuePerStremma());
        assertEquals(10L, response.getFieldId());
        assertEquals("Χωράφι 1", response.getFieldName());
    }

    @Test
    void testConstructor_withNullField_shouldFallbackToDefaults() {
        CropResult result = new CropResult();
        result.setField(null);
        result.setCropType("Καλαμπόκι");
        result.setAreaInStremma(0.0); 
        result.setProductionKg(500.0);
        result.setExpenses(200.0);
        result.setRevenue(700.0);
        result.setProfit(500.0);

        CropResultResponse response = new CropResultResponse(result);

        assertEquals("Καλαμπόκι", response.getCropType());
        assertEquals(0, response.getArea());
        assertEquals(0, response.getProductionPerStremma());
        assertEquals(0, response.getExpensesPerStremma());
        assertEquals(0, response.getRevenuePerStremma());
        assertEquals("Άγνωστο", response.getFieldName());
        assertNull(response.getFieldId());
    }
}
