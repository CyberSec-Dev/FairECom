package com.fairworld.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class node {
    private int id;
    private String productId;
    private String transactionHash;
    private String contractHash;
    private double price;
    private int number;
    private int strIndex;
}
