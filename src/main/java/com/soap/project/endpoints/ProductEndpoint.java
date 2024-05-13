package com.soap.project.endpoints;

import com.soap.project.entities.Product;
import com.soap.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;

import java.util.Optional;

@Endpoint
public class ProductEndpoint {

    private final ProductService productService;

    @Autowired
    public ProductEndpoint(ProductService productService) {
        this.productService = productService;
    }

    public Optional<Product> getProductById(int id) {
        return productService.getProductById(id);
    }

    public Product createProduct(Product product) {
        return productService.createProduct(product);
    }

    public Product updateProduct(Product product) {
        return productService.updateProduct(product);
    }

    public void deleteProduct(int id) {
        productService.deleteProduct(id);
    }
}
