package com.agrotical.repository;

import com.agrotical.entity.CropResult;
import com.agrotical.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CropResultRepository extends JpaRepository<CropResult, Long> {

    List<CropResult> findByField(Field field);
    
    List<CropResult> findByField_User_Id(Long userId);

    List<CropResult> findByField_Id(Long fieldId);
}
