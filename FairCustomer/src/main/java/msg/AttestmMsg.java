package msg;

public class AttestmMsg extends Message{
	/**
	 *
	 */
	private static final long serialVersionUID = -623522065819852026L;
	//private int pos;
	private byte[] hash;
	private String serviceID;
	private double price;
	//private ArrayList<Integer> rands;

	public AttestmMsg(byte[] hash, String serviceID, double price) {
		this.hash = hash;
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

	public byte[] getHash() {
		return hash;
	}

	public void setHash(byte[] hash) {
		this.hash = hash;
	}
}
