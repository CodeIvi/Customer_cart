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
public class OrderItem {
    private long id;
    private long orderId;
    private long productId;
    private String productName;
    private double unitPrice;
    private BigDecimal quantity;
    private BigDecimal lineTotal;
}