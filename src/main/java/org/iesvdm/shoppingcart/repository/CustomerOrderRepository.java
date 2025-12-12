package org.iesvdm.shoppingcart.repository;

import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
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

    public void updateCustomerOrder(CustomerOrder customerOrder) {
        String sql = """
            UPDATE customer_order
            SET status = ?,
                gross_total = ?,
                discount_total = ?,
                final_total = ?,
                payment_method = ?,
                payment_status = ?,
                payment_details = ?,
                billing_name = ?,
                billing_tax_id = ?,
                billing_street = ?,
                billing_city = ?,
                billing_postal_code = ?,
                billing_country = ?,
                shipping_name = ?,
                shipping_street = ?,
                shipping_city = ?,
                shipping_postal_code = ?,
                shipping_country = ?
            WHERE id = ?
            """;

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, customerOrder.getStatus());
            ps.setBigDecimal(2, customerOrder.getGrossTotal());
            ps.setBigDecimal(3, customerOrder.getDiscountTotal());
            ps.setBigDecimal(4, customerOrder.getFinalTotal());
            ps.setString(5, customerOrder.getPaymentMethod());
            ps.setString(6, customerOrder.getPaymentStatus());
            ps.setString(7, customerOrder.getPaymentDetails());
            ps.setString(8, customerOrder.getBillingName());
            ps.setString(9, customerOrder.getBillingTaxId());
            ps.setString(10, customerOrder.getBillingStreet());
            ps.setString(11, customerOrder.getBillingCity());
            ps.setString(12, customerOrder.getBillingPostalCode());
            ps.setString(13, customerOrder.getBillingCountry());
            ps.setString(14, customerOrder.getShippingName());
            ps.setString(15, customerOrder.getShippingStreet());
            ps.setString(16, customerOrder.getShippingCity());
            ps.setString(17, customerOrder.getShippingPostalCode());
            ps.setString(18, customerOrder.getShippingCountry());
            ps.setLong(19, customerOrder.getId());

            return ps;
        });
    }
}
