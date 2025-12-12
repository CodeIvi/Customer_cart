package org.iesvdm.shoppingcart.repository;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.shoppingcart.model.Coupon;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Slf4j
@Repository
public class CouponRepository {
    private JdbcTemplate jdbcTemplate;

    public CouponRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Coupon> getAllCoupons() {
        String sql = "SELECT * FROM coupon";
        List<Coupon> coupons = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Coupon.class)
        );

        log.info("Return {} coupons", coupons.size());
        return coupons;
    }

}
