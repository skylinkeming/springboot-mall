package com.stevelin.springbootmall.controller;

import com.stevelin.springbootmall.dto.ProductRequest;
import com.stevelin.springbootmall.model.Product;
import com.stevelin.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable int productId) {

        Product product = productService.getProductById(productId);

        if (product == null) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
