package com.stevelin.springbootmall.service.impl;

import com.stevelin.springbootmall.dao.ProductDao;
import com.stevelin.springbootmall.model.Product;
import com.stevelin.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(int id) {
        return productDao.getProductById(id);
    }
}
