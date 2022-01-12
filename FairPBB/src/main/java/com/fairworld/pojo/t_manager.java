package com.fairworld.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class t_manager {
    private int id;
    private String transactionId;
    private String serviceId;
    private String vendorId;
    private String bankId = "ChinaBank";
    private double price;
    private int position;
    private byte[] SignV;
    private byte[] SignB;
}
