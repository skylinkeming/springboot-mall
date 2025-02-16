package com.stevelin.springbootmall.service;

import com.stevelin.springbootmall.dto.ProductQueryParams;
import com.stevelin.springbootmall.dto.ProductRequest;
import com.stevelin.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(int id);
    Integer createProduct(ProductRequest product);
    void updateProduct(int id, ProductRequest product);
    void deleteProductById(int productId);
}
