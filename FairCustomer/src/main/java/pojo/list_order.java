package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class list_order {
    private int id;
    private String orderId;
    private int orderItemId;
    private String productId;
    private String sellerId;
    private Date shippingLimitDate;
    private double price;

    public list_order(String orderId, int orderItemId, String productId, String sellerId, Date shippingLimitDate, double price) {
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        this.productId = productId;
        this.sellerId = sellerId;
        this.shippingLimitDate = shippingLimitDate;
        this.price = price;
    }
}
