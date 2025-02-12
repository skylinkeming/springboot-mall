package com.stevelin.springbootmall.controller;

import com.stevelin.springbootmall.model.Product;
import com.stevelin.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
