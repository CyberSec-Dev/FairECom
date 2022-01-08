package msg;

import java.sql.Time;

public class Transaction extends Message {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5640986364438253804L;
	private String tranctionId;//????
    private String serviveId;//????
    private String clientId;//???
    private String venderId;//???
    private String bankId="ChinaBank";//????
    private double price;
    private Time time;//??????
    private int position;//¦Ë??
    
    
	public Transaction(String tranctionId, String serviveId, String clientId, String venderId, String bankId,
			double price) {
		super();
		this.tranctionId = tranctionId;
		this.serviveId = serviveId;
		this.clientId = clientId;
		this.venderId = venderId;
		this.bankId = bankId;
		this.price = price;
		
	}
	public Transaction(String tranctionId, String serviveId, String venderId,
					   double price) {
		super();
		this.tranctionId = tranctionId;
		this.serviveId = serviveId;
		this.venderId = venderId;
		this.price = price;

	}
	public String toString() {
		return tranctionId+","+serviveId+","+clientId+","+venderId+","+bankId+","+price;
	}
	public String getTranctionId() {
		return tranctionId;
	}

	public void setTranctionId(String tranctionId) {
		this.tranctionId = tranctionId;
	}

	public String getServiveId() {
		return serviveId;
	}

	public void setServiveId(String serviveId) {
		this.serviveId = serviveId;
	}

	public String getclientId() {
		return clientId;
	}

	public void setclientId(String clientId) {
		this.clientId = clientId;
	}

	public String getVenderId() {
		return venderId;
	}

	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	

}
