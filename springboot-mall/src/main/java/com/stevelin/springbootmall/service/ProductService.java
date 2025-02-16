package com.stevelin.springbootmall.service;

import com.stevelin.springbootmall.constant.ProductCategory;
import com.stevelin.springbootmall.dto.ProductRequest;
import com.stevelin.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(ProductCategory category, String search);
    Product getProductById(int id);
    Integer createProduct(ProductRequest product);
    void updateProduct(int id, ProductRequest product);
    void deleteProductById(int productId);
}
