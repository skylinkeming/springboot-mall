package com.stevelin.springbootmall.dao.impl;

import com.stevelin.springbootmall.dao.ProductDao;
import com.stevelin.springbootmall.dto.ProductRequest;
import com.stevelin.springbootmall.model.Product;
import com.stevelin.springbootmall.rowMapper.ProductRowMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts() {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product";
        Map<String, Object> map = new HashMap<>();

        return jdbcTemplate.query(sql, map, new ProductRowMapper());
    }

    @Override
    public Product getProductById(Integer productId) {

        String sql =
                "SELECT product_id, product_name, category, image_url, price, stock, description, "
                        + "created_date, last_modified_date " +
                        "FROM product WHERE product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> query = jdbcTemplate.query(sql, map, new ProductRowMapper());

        if (query != null && query.size() > 0) {
            return query.get(0);
        } else
            return null;

    }

    @Override
    public Integer createProduct(ProductRequest product) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, "
                + "description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, " +
                ":createdDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("productName", product.getProductName());
        map.put("category", product.getCategory().toString());
        map.put("imageUrl", product.getImageUrl());
        map.put("price", product.getPrice());
        map.put("stock", product.getStock());
        map.put("description", product.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProductById(Integer productId, ProductRequest product) {
        String sql = "UPDATE product SET product_name=:productName, category=:category, image_url=:imageUrl, " +
                " price=:price, stock=:stock, description=:description, last_modified_date=:lastModifiedDate"
                + " WHERE product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productName", product.getProductName());
        map.put("category", product.getCategory().toString());
        map.put("imageUrl", product.getImageUrl());
        map.put("price", product.getPrice());
        map.put("stock", product.getStock());
        map.put("description", product.getDescription());
        map.put("lastModifiedDate", new Date());
        map.put("productId", productId);
        jdbcTemplate.update(sql, map);

    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";
        jdbcTemplate.update(sql, new MapSqlParameterSource("productId", productId));
    }


}
