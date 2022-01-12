package com.fairworld.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private int id;
    private String productId;
    private String priceRoot;
    private double price;
}
