package com.soap.project.endpoints;

import com.soap.project.entities.Category;
import com.soap.project.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;

import java.util.Optional;

@Endpoint
public class CategoryEndpoint {

    private final CategoryService categoryService;

    @Autowired
    public CategoryEndpoint(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Optional<Category> getCategoryById(int id) {
        return categoryService.getCategoryById(id);
    }

    public Category createCategory(Category category) {
        return categoryService.createCategory(category);
    }

    public Category updateCategory(Category category) {
        return categoryService.updateCategory(category);
    }

    public void deleteCategory(int id) {
        categoryService.deleteCategory(id);
    }
}
