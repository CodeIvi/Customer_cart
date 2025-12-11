package org.iesvdm.shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CustomerOrder {
    private Long id;
    private String orderNumber;
    private java.sql.Timestamp createdAt;
    private String status; // NEW / PAID / FAILED / PENDING_PAYMENT
    private BigDecimal grossTotal;
    private BigDecimal discountTotal;
    private BigDecimal finalTotal;
    private long couponId;
    private String paymentMethod; // CARD / PAYPAL / BANK_TRANSFER
    private String paymentStatus; // PENDING / PAID / FAILED
    private String paymentDetails;

    private String billingName;
    private String billingTaxId;
    private String billingStreet;
    private String billingCity;
    private String billingPostalCode;
    private String billingCountry;

    private String shippingName;
    private String shippingStreet;
    private String shippingCity;
    private String shippingPostalCode;
    private String shippingCountry;
}