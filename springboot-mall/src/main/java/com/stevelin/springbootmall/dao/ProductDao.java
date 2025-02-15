package com.stevelin.springbootmall.dao;

import com.stevelin.springbootmall.dto.ProductRequest;
import com.stevelin.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer id);
    Integer createProduct(ProductRequest product);
    void updateProductById(Integer productId, ProductRequest product);

}
