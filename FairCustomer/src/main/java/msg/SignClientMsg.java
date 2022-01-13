package msg;

import java.lang.reflect.Array;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

public class SignClientMsg extends Message {
	/**
	 *
	 */
	private static final long serialVersionUID = 7714433424201752143L;


	private ArrayList<byte[]> msg;
	private ArrayList<byte[]> signMsg;
	//private RSAPublicKey publicKey;

	public SignClientMsg(ArrayList<byte[]> msg, ArrayList<byte[]> signMsg) {
		this.msg = msg;
		this.signMsg = signMsg;
	}

	public ArrayList<byte[]> getMsg() {
		return msg;
	}

	public void setMsg(ArrayList<byte[]> msg) {
		this.msg = msg;
	}

	public ArrayList<byte[]> getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(ArrayList<byte[]> signMsg) {
		this.signMsg = signMsg;
	}
}