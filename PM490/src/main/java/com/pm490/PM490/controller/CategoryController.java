package com.pm490.PM490.controller;

import com.pm490.PM490.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }
}
