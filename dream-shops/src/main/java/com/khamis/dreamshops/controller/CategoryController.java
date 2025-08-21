package com.khamis.dreamshops.controller;

import com.khamis.dreamshops.exceptions.CategoryNotFoundException;
import com.khamis.dreamshops.model.Category;
import com.khamis.dreamshops.response.ApiResponse;
import com.khamis.dreamshops.service.categoryService.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse>getallcategories(@RequestBody Category category){

        try {
            List<Category>categories=categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Founded",categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error",INTERNAL_SERVER_ERROR));
        }

    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse>addcategory(@RequestBody Category category){
        try {
            Category category1=categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Success",category1));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse>getCategoryByName(@PathVariable String name){
        try {
            Category category=categoryService.getCategoryByName(name);

            return ResponseEntity.ok(new ApiResponse("Success",category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }


    }
    @GetMapping("category/{id}/category")
    public ResponseEntity<ApiResponse>getCategoryById(@PathVariable Long id){
        try {
            Category category=categoryService.getCategoryById(id);

            return ResponseEntity.ok(new ApiResponse("Success",category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }


    }
    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse>deleteCategoryById(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);

            return ResponseEntity.ok(new ApiResponse("Success",null));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }


    }
    @PutMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse>updateCategory(@RequestBody Category category,@PathVariable Long id){

        try {
            Category category1=categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Update Seccess",category1));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }




}
