package com.uniruy.eventhub.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uniruy.eventhub.dto.CategoryDTO;
import com.uniruy.eventhub.services.CategoryService;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    
    @Autowired
    public CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategory() {
        return categoryService.getALLCategory();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping(value = "/search")
    public List<CategoryDTO> getCategoryByName(@RequestParam String name) {
        return categoryService.getCategoryByName(name);
    }

    @PostMapping
    public ResponseEntity<?> postCategory(@RequestBody CategoryDTO body) {
        return categoryService.postCategory(body);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> putCategory(@PathVariable Long id, @RequestBody CategoryDTO body) {
        return categoryService.putCategory(body, id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}
