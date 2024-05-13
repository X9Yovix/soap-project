package com.soap.project.endpoints;

import com.soap.project.entities.CategoryEntity;
import com.soap.project.generated.categories.*;
import com.soap.project.services.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

@Endpoint
public class CategoryEndpoint {

    private final CategoryService categoryService;
    private static final String NameSpace = "http://generated.project.soap.com/categories";

    @Autowired
    public CategoryEndpoint(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PayloadRoot(namespace = NameSpace, localPart = "getCategoryRequest")
    @ResponsePayload
    public GetCategoryResponse getCategory(@RequestPayload GetCategoryRequest request) {
        Optional<CategoryEntity> category = categoryService.getCategoryById(request.getId());

        GetCategoryResponse response = new GetCategoryResponse();
        if (category.isPresent()) {
            Category categoryGenerated = new Category();
            categoryGenerated.setId(category.get().getId());
            categoryGenerated.setName(category.get().getName());
            response.setCategory(categoryGenerated);
        }
        return response;
    }

    @PayloadRoot(namespace = NameSpace, localPart = "createCategoryRequest")
    @ResponsePayload
    public CreateCategoryResponse createCategory(@RequestPayload CreateCategoryRequest request) {
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtils.copyProperties(request.getCategory(), categoryEntity);

        CategoryEntity createdCategory = categoryService.createCategory(categoryEntity);

        CreateCategoryResponse response = new CreateCategoryResponse();
        response.setMessage("Category created successfully with ID: " + createdCategory.getId());
        return response;
    }

    @PayloadRoot(namespace = NameSpace, localPart = "updateCategoryRequest")
    @ResponsePayload
    public UpdateCategoryResponse updateCategory(@RequestPayload UpdateCategoryRequest request) {
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtils.copyProperties(request.getCategory(), categoryEntity);

        CategoryEntity updatedCategory = categoryService.updateCategory(categoryEntity);

        UpdateCategoryResponse response = new UpdateCategoryResponse();
        response.setMessage("Category updated successfully with ID: " + updatedCategory.getId());
        return response;
    }

    @PayloadRoot(namespace = NameSpace, localPart = "deleteCategoryRequest")
    @ResponsePayload
    public DeleteCategoryResponse deleteCategory(@RequestPayload DeleteCategoryRequest request) {
        categoryService.deleteCategory(request.getId());

        DeleteCategoryResponse response = new DeleteCategoryResponse();
        response.setMessage("Category deleted successfully");
        return response;
    }
}
