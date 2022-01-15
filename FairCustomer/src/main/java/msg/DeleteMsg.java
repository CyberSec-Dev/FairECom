package msg;


public class DeleteMsg extends Message {

	private static final long serialVersionUID = -252494816195161806L;
	private String hash;

	public DeleteMsg(String hash) {
		this.hash = hash;
	}
	
	public String getHash() {
		return hash;
	}
}
