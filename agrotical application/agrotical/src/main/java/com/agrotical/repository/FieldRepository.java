package com.agrotical.repository;


import com.agrotical.entity.Field;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.agrotical.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

    List<Field> findByUser_Id(Long userId);

    
}
