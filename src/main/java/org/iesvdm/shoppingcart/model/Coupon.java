package org.iesvdm.shoppingcart.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {
    private Long id;
    private String code;
    private String description;
    private String discountType; // PERCENT / FIXED_AMOUNT
    private BigDecimal discountValue;
    private Boolean active;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
}