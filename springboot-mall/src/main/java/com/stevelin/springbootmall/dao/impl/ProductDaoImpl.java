package com.stevelin.springbootmall.dao.impl;

import com.stevelin.springbootmall.dao.ProductDao;
import com.stevelin.springbootmall.dto.ProductQueryParams;
import com.stevelin.springbootmall.dto.ProductRequest;
import com.stevelin.springbootmall.model.Product;
import com.stevelin.springbootmall.rowMapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date" +
                " FROM product WHERE 1=1";
        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql, map, productQueryParams);

        // 排序
        // 注意 jdbc的Order By 語法只能透過string拼接 無法透過map去加上去
        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        //分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

        return jdbcTemplate.query(sql, map, new ProductRowMapper());
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "SELECT count(*) FROM product WHERE 1=1";
        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql, map, productQueryParams);

        Integer count = jdbcTemplate.queryForObject(sql, map, Integer.class);

        return count;
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

    public void updateStock(Integer productId, Integer stock) {
        String sql = "UPDATE product SET stock = :stock WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("stock", stock);
        map.put("productId", productId);
        map.put("lastModifiedDate", new Date());

        jdbcTemplate.update(sql, map);
    }

    private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {
        if (productQueryParams.getCategory() != null) {
            sql = sql + " AND category = :category";
            map.put("category", productQueryParams.getCategory().name());
        }
        if (productQueryParams.getSearch() != null) {
            sql = sql + " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }
        return sql;
    }
}
