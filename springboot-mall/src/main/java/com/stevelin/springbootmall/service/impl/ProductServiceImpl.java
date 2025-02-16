package com.stevelin.springbootmall.service.impl;

import com.stevelin.springbootmall.constant.ProductCategory;
import com.stevelin.springbootmall.dao.ProductDao;
import com.stevelin.springbootmall.dto.ProductQueryParams;
import com.stevelin.springbootmall.dto.ProductRequest;
import com.stevelin.springbootmall.model.Product;
import com.stevelin.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Product getProductById(int id) {
        return productDao.getProductById(id);
    }

    @Override
    public Integer createProduct(ProductRequest product) {
        return productDao.createProduct(product);
    }

    @Override
    public void updateProduct(int id, ProductRequest product) {
        productDao.updateProductById(id, product);
    }

    @Override
    public void deleteProductById(int productId) {
        productDao.deleteProductById(productId);
    }
}
