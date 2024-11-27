package com.example.capstone1.Controller;
import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {


    final private CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity getCategories() {
        categoryService.categoryaddService();
        return ResponseEntity.status(200).body(categoryService.getCategories());
    }

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String response = categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse(response));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable String id, @RequestBody Category updatedCategory, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        boolean response = categoryService.updateCategory(id, updatedCategory);
        if (response){
        return ResponseEntity.status(200).body(new ApiResponse("updated "));}
        return ResponseEntity.status(400).body(new ApiResponse("not found"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id) {
        boolean response = categoryService.deleteCategory(id);

        if (response){
            return ResponseEntity.status(200).body(new ApiResponse("deleted "));}
        return ResponseEntity.status(400).body(new ApiResponse("not found"));

    }

    @GetMapping("/count")
    public ResponseEntity countCategories() {
        int count = categoryService.countCategories();
        return ResponseEntity.status(200).body(new ApiResponse("Total categories: " + count));
    }
}
