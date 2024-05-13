package com.soap.project.endpoints;

import com.soap.project.entities.CategoryEntity;
import com.soap.project.entities.ProductEntity;
import com.soap.project.generated.products.*;
import com.soap.project.services.CategoryService;
import com.soap.project.services.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

@Endpoint
public class ProductEndpoint {

    private final ProductService productService;
    private final CategoryService categoryService;
    private static final String NameSpace = "http://generated.project.soap.com/products";

    @Autowired
    public ProductEndpoint(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PayloadRoot(namespace = NameSpace, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        Optional<ProductEntity> product = productService.getProductById(request.getId());

        GetProductResponse response = new GetProductResponse();
        if (product.isPresent()) {
            Product productGenerated = new Product();
            productGenerated.setId(product.get().getId());
            productGenerated.setName(product.get().getName());
            productGenerated.setPrice(product.get().getPrice());
            productGenerated.setCategoryId(product.get().getCategory().getId());
            response.setProduct(productGenerated);
        }
        return response;
    }

    @PayloadRoot(namespace = NameSpace, localPart = "createProductRequest")
    @ResponsePayload
    public CreateProductResponse createProduct(@RequestPayload CreateProductRequest request) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(request.getProduct(), productEntity);

        Optional<CategoryEntity> categoryEntityOptional = categoryService.getCategoryById(request.getProduct().getCategoryId());
        if (categoryEntityOptional.isPresent()) {
            CategoryEntity categoryEntity = categoryEntityOptional.get();
            productEntity.setCategory(categoryEntity);
            ProductEntity createdProduct = productService.createProduct(productEntity);

            CreateProductResponse response = new CreateProductResponse();
            response.setMessage("Product created successfully with ID: " + createdProduct.getId());
            return response;
        } else {
            CreateProductResponse response = new CreateProductResponse();
            response.setMessage("Failed to create product. Category not found");
            return response;
        }
    }

    @PayloadRoot(namespace = NameSpace, localPart = "updateProductRequest")
    @ResponsePayload
    public UpdateProductResponse updateProduct(@RequestPayload UpdateProductRequest request) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(request.getProduct(), productEntity);

        Optional<CategoryEntity> categoryEntityOptional = categoryService.getCategoryById(request.getProduct().getCategoryId());
        if (categoryEntityOptional.isPresent()) {
            CategoryEntity categoryEntity = categoryEntityOptional.get();
            productEntity.setCategory(categoryEntity);
            ProductEntity updatedProduct = productService.updateProduct(productEntity);

            UpdateProductResponse response = new UpdateProductResponse();
            response.setMessage("Product updated successfully with ID: " + updatedProduct.getId());
            return response;
        } else {
            UpdateProductResponse response = new UpdateProductResponse();
            response.setMessage("Failed to update product. Category not found");
            return response;
        }
    }

    @PayloadRoot(namespace = NameSpace, localPart = "deleteProductRequest")
    @ResponsePayload
    public DeleteProductResponse deleteProduct(@RequestPayload DeleteProductRequest request) {
        productService.deleteProduct(request.getId());

        DeleteProductResponse response = new DeleteProductResponse();
        response.setMessage("Product deleted successfully");
        return response;
    }

}
