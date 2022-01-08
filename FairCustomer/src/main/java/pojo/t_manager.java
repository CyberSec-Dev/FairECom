package pojo;

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


    public t_manager(String transactionId, String serviceId, String vendorId, String bankId, double price, int position, byte[] signV, byte[] signB) {
        this.transactionId = transactionId;
        this.serviceId = serviceId;
        this.vendorId = vendorId;
        this.bankId = bankId;
        this.price = price;
        this.position = position;
        SignV = signV;
        SignB = signB;
    }
}
