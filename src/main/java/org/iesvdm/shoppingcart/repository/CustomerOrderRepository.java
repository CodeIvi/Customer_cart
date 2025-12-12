package org.iesvdm.shoppingcart.repository;

import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CustomerOrderRepository {
    private JdbcTemplate jdbcTemplate;

    public CustomerOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CustomerOrder> getAllCustomerOrders() {
        String sql = "SELECT * FROM customer_order";

        List<CustomerOrder> orders = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(CustomerOrder.class)
        );

        return orders;
    }
}
