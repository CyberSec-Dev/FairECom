package msg;

import java.sql.Time;

public class TransactionSign extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = -403075498127453558L;
	private String tranctionId;// 交易
	private String serviceId;// 服务
	private String vendorId;// 商户
	private String bankId;// 银行
	private double price;
	private int position;// 位置
	byte[] SignV;
	byte[] SignB;
	
	private Time time;// 年月日
	public TransactionSign(String tranctionId) {
		this.tranctionId=tranctionId;
		
	}
	public TransactionSign(String tranctionId, int position) {
		this.tranctionId=tranctionId;
		this.position=position;
		
	}
	public TransactionSign(String tranctionId, String serviceId, String vendorId, String bankId, double price,
                           int position) {
		super();
		this.tranctionId = tranctionId;
		this.serviceId = serviceId;
		this.vendorId = vendorId;
		this.bankId = bankId;
		this.price = price;
		this.position = position;
	
	}

	public TransactionSign(String tranctionId, String serviceId, String vendorId, String bankId, double price, int position, byte[] signV, byte[] signB) {
		this.tranctionId = tranctionId;
		this.serviceId = serviceId;
		this.vendorId = vendorId;
		this.bankId = bankId;
		this.price = price;
		this.position = position;
		SignV = signV;
		SignB = signB;
	}

	public String toString() {
		return tranctionId+","+serviceId+","+vendorId+","+bankId+","+price+","+position;
	}
	
	public String getTranctionId() {
		return tranctionId;
	}
	public void setTranctionId(String tranctionId) {
		this.tranctionId = tranctionId;
	}
	public String getserviceId() {
		return serviceId;
	}
	public void setserviceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getvendorId() {
		return vendorId;
	}
	public void setvendorId(String vendorId) {
		this.vendorId = vendorId;
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
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public byte[] getSignV() {
		return SignV;
	}
	public void setSignV(byte[] signV) {
		SignV = signV;
	}
	public byte[] getSignB() {
		return SignB;
	}
	public void setSignB(byte[] signB) {
		SignB = signB;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	
	

}
