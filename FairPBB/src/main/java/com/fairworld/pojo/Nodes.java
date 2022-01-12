package com.fairworld.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nodes {
    private int id;
    private String productId;
    private String root;
    private String priceRoot;
    private String transactionHash;
    private String contractHash;
    private double price;
    private int number;
}
