package com.agrotical.controller;


import com.agrotical.entity.Field;
import com.agrotical.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/fields")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Field> createField(@PathVariable Long userId, @RequestBody Field field) {
        Field saved = fieldService.createField(userId, field);
        System.out.println("DEBUG saved field id: " + saved.getId());
        return ResponseEntity.ok(saved);
    }

    
    @PostMapping
    public ResponseEntity<Field> createFieldWithUserInBody(@RequestBody Field field) {
        if (field.getUser() == null || field.getUser().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Field saved = fieldService.createField(field.getUser().getId(), field);
        return ResponseEntity.ok(saved);
    }


    @GetMapping
    public ResponseEntity<List<Field>> getAllFields() {
        return ResponseEntity.ok(fieldService.getAllFields());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Field> getFieldById(@PathVariable Long id) {
        return fieldService.getFieldById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/user/{userId}")
        public ResponseEntity<List<Field>> getFieldsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(fieldService.getFieldsByUserId(userId));
    }


}
