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
public class Product {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
}