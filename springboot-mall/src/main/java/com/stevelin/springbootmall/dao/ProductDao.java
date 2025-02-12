package com.stevelin.springbootmall.dao;

import com.stevelin.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer id);

}
