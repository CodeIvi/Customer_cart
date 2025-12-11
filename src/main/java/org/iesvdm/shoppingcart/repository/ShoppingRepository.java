package org.iesvdm.shoppingcart.repository;

import org.iesvdm.shoppingcart.model.CustomerOrder;
import org.iesvdm.shoppingcart.model.OrderItem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class ShoppingRepository {
    private JdbcTemplate jdbcTemplate;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ShoppingRepository.class);

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

    public List<OrderItem> getAllOrders( long id) {
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        List<OrderItem> items = jdbcTemplate.query(sql
             /*   (rs, rowNum) -> new OrderItem(
                        rs.getLong("id"),
                        rs.getLong("order_id"),
                        rs.getLong("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getBigDecimal("unit_price"),
                        rs.getBigDecimal("total_price"))*/
                ,new BeanPropertyRowMapper<>(OrderItem.class)
                ,id
        );



        log.info("Devueltos {} order items", items.size());
        return items;
    }


public void update(OrderItem orderItem, Long id) {

    String sql = """
            update order_item
            set product_name = ?,
                quantity = ?,
                unit_price = ?,
                line_total = ?
            where id = ?
            """;

    jdbcTemplate.update(con -> {
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, orderItem.getProductName());
        ps.setBigDecimal(2, orderItem.getQuantity());
        ps.setBigDecimal(3, orderItem.getUnitPrice());
        ps.setBigDecimal(4, orderItem.getLineTotal());
        ps.setLong(5, id);

        return ps;
    });
}

public OrderItem findOrderItemById(Long id) {
    String sql = "SELECT * FROM order_item WHERE id = ?";
    return jdbcTemplate.queryForObject(sql,
            (ResultSet rs, int rowNum) -> OrderItem.builder()
                    .id(rs.getLong("id"))
                    .orderId(rs.getLong("order_id"))
                    .productId(rs.getLong("product_id"))
                    .productName(rs.getString("product_name"))
                    .quantity(rs.getBigDecimal("quantity"))
                    .unitPrice(rs.getBigDecimal("unit_price"))
                    .lineTotal(rs.getBigDecimal("line_total"))
                    .build(),
            id);
}

    public boolean delete(Long id) {

        String sql = "DELETE FROM order_item WHERE id = ?";
        int filasEliminadas = jdbcTemplate.update(sql, id);
        return filasEliminadas > 0; // Devuelve true si se borró al menos una fila

    }

    public OrderItem createOrderItem(OrderItem orderItem) {

        String sql = """
                insert into order_item(order_id, product_id, product_name, quantity, unit_price, line_total)
                values(?, ?, ?, ?, ?, ?)
                """;

        String[] ids = {"id"};

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {

            PreparedStatement ps = con.prepareStatement(sql, ids);

            ps.setLong(1, orderItem.getOrderId());
            ps.setLong(2, orderItem.getProductId());
            ps.setString(3, orderItem.getProductName());
            ps.setBigDecimal(4, orderItem.getQuantity());
            ps.setBigDecimal(5, orderItem.getUnitPrice());
            ps.setBigDecimal(6, orderItem.getLineTotal());

            return ps;
        }, keyHolder);

        orderItem.setId(keyHolder.getKey().longValue());

        return orderItem;
    }
}
