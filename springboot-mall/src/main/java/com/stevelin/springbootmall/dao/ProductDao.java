package com.stevelin.springbootmall.dao;

import com.stevelin.springbootmall.constant.ProductCategory;
import com.stevelin.springbootmall.dto.ProductRequest;
import com.stevelin.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts(ProductCategory category, String search);
    Product getProductById(Integer id);
    Integer createProduct(ProductRequest product);
    void updateProductById(Integer productId, ProductRequest product);
    void deleteProductById(Integer productId);
}
