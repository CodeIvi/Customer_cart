package org.iesvdm.shoppingcart.service;

import org.iesvdm.shoppingcart.model.Coupon;
import org.iesvdm.shoppingcart.model.OrderItem;
import org.iesvdm.shoppingcart.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CouponService {
    private CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<Coupon> couponList(){
        return couponRepository.getAllCoupons();
    }

    public BigDecimal applyDiscount(String discount, List<OrderItem> orderItems) {
        BigDecimal cuenta = BigDecimal.ZERO;

        // Sumar todos los lineTotal
        for (OrderItem item : orderItems) {
            cuenta = cuenta.add(item.getLineTotal());
        }

        // Aplicar descuento
        if ("discount10".equalsIgnoreCase(discount)) {
            cuenta = cuenta.subtract(cuenta.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
        } else if ("minus5".equalsIgnoreCase(discount)) {
            cuenta = cuenta.subtract(BigDecimal.valueOf(5));
        } else {
            // por defecto, un 20%
            cuenta = cuenta.subtract(cuenta.multiply(BigDecimal.valueOf(20)).divide(BigDecimal.valueOf(100)));
        }

        return cuenta;
    }

    public BigDecimal Discount(String discountCode, List<OrderItem> orderItems) {
        BigDecimal total = BigDecimal.ZERO;

        // Sumamos todos los lineTotal
        for (OrderItem item : orderItems) {
            total = total.add(item.getLineTotal());
        }

        BigDecimal discountAmount = BigDecimal.ZERO;

        // Calculamos el descuento según el código
        if ("discount10".equalsIgnoreCase(discountCode)) {
            discountAmount = total.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100));
        } else if ("minus5".equalsIgnoreCase(discountCode)) {
            discountAmount = BigDecimal.valueOf(5);
        } else {
            // por defecto, 20%
            discountAmount = total.multiply(BigDecimal.valueOf(20)).divide(BigDecimal.valueOf(100));
        }

        return discountAmount;
    }
}
