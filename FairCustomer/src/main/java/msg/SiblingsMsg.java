package msg;


public class SiblingsMsg extends Message{
	private  static final long serialVersionUID = 3944657955000271600L;
    private  byte[] hash;
    private  int direction;
	public SiblingsMsg(byte[] hash,int direction) {
		// TODO Auto-generated constructor stub
		this.hash=hash;
		this.direction=direction;
	}
	
	public  byte[] getHash() {
		return hash;
	}
	public  void setHash(byte[] hash) {
		this.hash = hash;
	}
	public  int getDirection() {
		return direction;
	}
	public  void setDirection(int direction) {
		this.direction = direction;
	}

}
