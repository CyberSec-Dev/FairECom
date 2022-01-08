package msg;

public class AttestmMsg extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = -623522065819852026L;
	private int pos;
	private String serviceID;
	private double price;
	//private ArrayList<Integer> rands;

	public AttestmMsg(int pos, String serviceID, double price) {
		this.pos = pos;
		this.serviceID = serviceID;
		this.price = price;
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

	public int getPos() {
		return pos;
	}


}
