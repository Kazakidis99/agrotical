package com.agrotical.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CropCalculationRequestTest {

    @Test
    void testSettersAndGetters() {
        CropCalculationRequest dto = new CropCalculationRequest();
        dto.setCropType("Σιτάρι");
        dto.setAreaInStremma(20);
        dto.setFertilized(true);
        dto.setSprayed(false);
        dto.setIrrigated(true);
        dto.setFieldId(1L);
        dto.setAreaInStremma(20);

        assertThat(dto.getCropType()).isEqualTo("Σιτάρι");
        assertThat(dto.getAreaInStremma()).isEqualTo(20);
        assertThat(dto.isFertilized()).isTrue();
        assertThat(dto.isSprayed()).isFalse();
        assertThat(dto.isIrrigated()).isTrue();
        assertThat(dto.getFieldId()).isEqualTo(1L);
        assertThat(dto.getAreaInStremma()).isEqualTo(20);
    }
}
