package org.iesvdm.shoppingcart.repository;

import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
@Repository
public class ShoppingRepository {
    private JdbcTemplate jdbcTemplate;

    public ShoppingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public CustomerOrder findById(Long id) {
    String sql = "SELECT * FROM customer_order WHERE id = ?";
    return jdbcTemplate.queryForObject(sql,
            (ResultSet rs, int rowNum) -> CustomerOrder.builder()
                    .id(rs.getLong("id"))
                    .orderNumber(rs.getString("order_number"))
                    .createdAt(rs.getTimestamp("created_at"))
                    .status(rs.getString("status"))
                    .grossTotal(rs.getBigDecimal("gross_total"))
                    .discountTotal(rs.getBigDecimal("discount_total"))
                    .finalTotal(rs.getBigDecimal("final_total"))
                    .couponId(rs.getLong("coupon_id"))
                    .paymentMethod(rs.getString("payment_method"))
                    .paymentStatus(rs.getString("payment_status"))
                    .paymentDetails(rs.getString("payment_details"))
                    .billingName(rs.getString("billing_name"))
                    .billingTaxId(rs.getString("billing_tax_id"))
                    .billingStreet(rs.getString("billing_street"))
                    .billingCity(rs.getString("billing_city"))
                    .billingPostalCode(rs.getString("billing_postal_code"))
                    .billingCountry(rs.getString("billing_country"))
                    .shippingName(rs.getString("shipping_name"))
                    .shippingStreet(rs.getString("shipping_street"))
                    .shippingCity(rs.getString("shipping_city"))
                    .shippingPostalCode(rs.getString("shipping_postal_code"))
                    .shippingCountry(rs.getString("shipping_country"))
                    .build(),
            id);

}

}
