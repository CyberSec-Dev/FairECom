package msg;

import java.util.ArrayList;

public class AttestcMsg extends Message {
    private static final long serialVersionUID = 5919099131628251991L;
    private String serviceID;
    private double price;
    private ArrayList<Integer> rands;

    public AttestcMsg(String serviceID, double price, ArrayList<Integer> rands) {
        this.serviceID = serviceID;
        this.price = price;
        this.rands = rands;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Integer> getRands() {
        return rands;
    }

    public void setRands(ArrayList<Integer> rands) {
        this.rands = rands;
    }
}
