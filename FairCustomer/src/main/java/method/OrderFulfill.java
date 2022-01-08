package method;

import msg.SignBankMsg;
import msg.SignClientMsg;
import msg.Transaction;
import utils.query;

import javax.crypto.Cipher;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderFulfill {

	public OrderFulfill() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<RSAPublicKey> orderFulfill(ObjectOutputStream oosVendor, ObjectInputStream oisVendor) throws Exception {
		//Transaction tid = new Transaction("t1000", "book", "13700001111", "vendor1", "ChinaBank", 100);
		System.out.println("Order-fulfill:create transactions.");
		//Map<Double, List<Transaction>> map=query.getList_order("154e7e31ebfa092203795c972e5804a6");
		//List<Transaction> transactions=map.get(23.99);
		///List<Transaction> transactions=map.get(19.99);
		//Map<Double, List<Transaction>> map=query.getList_order("aca2eb7d00ea1a7b8ebd4e68314663af");
		//List<Transaction> transactions=map.get(69.9);
		//Map<Double, List<Transaction>> map=query.getList_order("437c05a395e9e47f9762e677a7068ce7");
		//	List<Transaction> transactions=map.get(53.79);
		//List<Transaction> transactions=map.get(47.65);
		//List<Transaction> transactions=map.get(53.5);
		//List<Transaction> transactions=map.get(50.21);

		Map<Double, List<Transaction>> map=query.getList_order("99a4788cb24856965c36a24e339b6058");
		List<Transaction> transactions=map.get(74.0);
		//List<Transaction> transactions=map.get(79.9);

		//List<Transaction> transactions=map.get(89.9);


		//System.out.println("finish query");


		System.out.println("Transaction number:"+transactions.size());
		System.out.println("Transcation id:");
		for(int i=0;i<transactions.size();i++) {
			transactions.get(i).setclientId("13011112222");
			System.out.println(i+1+"."+transactions.get(i).getTranctionId());
		}
		// System.out.println(tid.toString());
		// RSA
		int RSAkeylen = 2048;
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(RSAkeylen);
		KeyPair kp = kpg.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
		ArrayList<byte[]> signC=new ArrayList<>();
		ArrayList<byte[]> msg=new ArrayList<>();
		for(int i=0;i<transactions.size();i++) {
			byte[] signClient = RSA(privateKey, transactions.get(i).toString().getBytes());
			msg.add(transactions.get(i).toString().getBytes());
			signC.add(signClient);
		}
		SignClientMsg signClientMsg=new SignClientMsg(msg,signC, publicKey);
		oosVendor.writeObject(signClientMsg);
		oosVendor.flush();
		System.out.println("C send sig. to V.");
		SignBankMsg signBankMsg=(SignBankMsg)oisVendor.readObject();
		System.out.println("C read sig. of V and B.");
		Boolean b=true;
		ArrayList<byte[]> msg111=signBankMsg.getMsg();
		ArrayList<byte[]> signB1=signBankMsg.getSignBank1();
		ArrayList<byte[]> signB2=signBankMsg.getSignBank2();
		RSAPublicKey publicKeyBank=signBankMsg.getPublicKeyBank();
		ArrayList<byte[]> signV1=signBankMsg.getSignVendor1();
		ArrayList<byte[]> signV2=signBankMsg.getSignVendor2();
		RSAPublicKey publicKeyVendor=signBankMsg.getPublicKeyVendor();
		for(int i=0;i<msg111.size();i++) {
			byte[] msg222 = DERSA(publicKeyBank, signB1.get(i));
			byte[] msg333 = DERSA(publicKeyBank, signB2.get(i));
			String[] array1 = new String(msg111.get(i)).split(",");
			String msg444 = array1[0] + "," + array1[1] + "," + array1[3] + "," + array1[4] + "," + array1[5] + "," + array1[6];
			if (new String(msg222).equals(new String(msg111.get(i))) && new String(msg333).equals(msg444)) {
				System.out.println("B's sig. verf. success. tid="+array1[0]);
			} else {
				System.out.println("B's sig. verf. fail. tid="+array1[0]);
				b=false;
			}
			byte[] msgV1 = DERSA(publicKeyVendor, signV1.get(i));
			byte[] msgV2 = DERSA(publicKeyVendor, signV2.get(i));
			if (new String(msgV1).equals(new String(msg111.get(i))) && new String(msgV2).equals(msg444)) {
				System.out.println("V's sig. verf. success. tid="+array1[0]);
			} else {
				System.out.println("V's sig. verf. fail. tid="+array1[0]);
				b=false;
			}
		}
		if(b.equals(true)){
		    System.out.println("Order-fulfill success!");
		}else{
			System.out.println("Order-fulfill fail!");
		}

		ArrayList<RSAPublicKey> publicKeys=new ArrayList<>();
		publicKeys.add(signBankMsg.getPublicKeyVendor());
		publicKeys.add(signBankMsg.getPublicKeyBank());
		return publicKeys;


	}

	/**
	 * RSA sign
	 * 
	 * @param privateKey
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static byte[] RSA(PrivateKey privateKey, byte[] message) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(message);
	}

	/**
	 * RSA verify
	 * 
	 * @param PUBK
	 * @param encrypted
	 * @return
	 * @throws Exception
	 */
	public static byte[] DERSA(PublicKey PUBK, byte[] encrypted) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, PUBK);
		return cipher.doFinal(encrypted);
	}

}
