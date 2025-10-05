package com.example.playground.category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        categoryService.saveCategory(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Category> findAllCategorys() {
        return categoryService.findAllCategorys();
    }

    @GetMapping("/{categoryId}")
    public Category findSingleCategory(@PathVariable Long categoryId) {
        return categoryService.findCategoryById(categoryId);
    }
}
