package org.iesvdm.shoppingcart.repository;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.shoppingcart.model.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
@Repository
@Slf4j
public class ProductsRepository {
    private JdbcTemplate jdbcTemplate;

    public ProductsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product";
        List<Product> products = jdbcTemplate.query(sql
                ,new BeanPropertyRowMapper<>(Product.class)
        );

        log.info("Return {} products  ", products.size());
        return products;
    }

    public Product findById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
                (ResultSet rs, int rowNum) -> Product.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getBigDecimal("price"))
                        .active(rs.getBoolean("active"))
                        .build(),
                id
        );
    }
}
