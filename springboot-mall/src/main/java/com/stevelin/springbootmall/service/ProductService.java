package com.stevelin.springbootmall.service;

import com.stevelin.springbootmall.dto.ProductRequest;
import com.stevelin.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(int id);
    Integer createProduct(ProductRequest product);
    void updateProduct(int id, ProductRequest product);
    void deleteProductById(int productId);
}
