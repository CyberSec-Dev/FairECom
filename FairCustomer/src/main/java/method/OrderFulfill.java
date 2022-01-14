package method;

import msg.RSAKey1;
import msg.SignBankMsg;
import msg.SignClientMsg;
import msg.Transaction;
import utils.Sha256Hash;
import utils.query;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

public class OrderFulfill {

	public OrderFulfill() {
		// TODO Auto-generated constructor stub
	}

	public void orderFulfill(ObjectOutputStream oosVendor, ObjectInputStream oisVendor) throws Exception {

		System.out.println("Order-fulfill:create transactions.");


		ArrayList<Transaction> transactions=new ArrayList<>();
		System.out.println("Please enter the product id:");
		Scanner scan=new Scanner(System.in);
		String productId=scan.next();
		Random rd = new Random();
		String s=String.valueOf(rd.nextInt(10000));
		String tid=new BigInteger(1,hash(s.getBytes())).toString(16).substring(0,30);
		Transaction transaction=new Transaction(tid,productId,"13011112222","book","ChinaBank",666);
		transactions.add(transaction);

		FileInputStream fin=new FileInputStream("./customerRSAKey");
		ObjectInputStream in=new ObjectInputStream(fin);
		ArrayList<RSAKey1> keys=(ArrayList<RSAKey1>) in.readObject();
		RSAPrivateKey privateKey = keys.get(0).getPrivateKey();
		RSAPublicKey publicKey = keys.get(0).getPublicKey();
		RSAPublicKey publicKeyBank=keys.get(2).getPublicKey();
		RSAPublicKey publicKeyVendor=keys.get(1).getPublicKey();
		ArrayList<byte[]> signC=new ArrayList<>();
		ArrayList<byte[]> msg=new ArrayList<>();
		for(int i=0;i<transactions.size();i++) {
			byte[] signClient = RSA(privateKey, transactions.get(i).toString().getBytes());
			msg.add(transactions.get(i).toString().getBytes());
			signC.add(signClient);
		}
		SignClientMsg signClientMsg=new SignClientMsg(msg,signC);
		oosVendor.writeObject(signClientMsg);
		oosVendor.flush();
		System.out.println("C send sig. to V.");
		SignBankMsg signBankMsg=(SignBankMsg)oisVendor.readObject();
		System.out.println("C read sig. of V and B.");
		Boolean b=true;
		ArrayList<byte[]> msg111=signBankMsg.getMsg();
		ArrayList<byte[]> signB1=signBankMsg.getSignBank1();
		ArrayList<byte[]> signB2=signBankMsg.getSignBank2();
		//RSAPublicKey publicKeyBank=signBankMsg.getPublicKeyBank();
		ArrayList<byte[]> signV1=signBankMsg.getSignVendor1();
		ArrayList<byte[]> signV2=signBankMsg.getSignVendor2();
		//RSAPublicKey publicKeyVendor=signBankMsg.getPublicKeyVendor();
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

		//ArrayList<RSAPublicKey> publicKeys=new ArrayList<>();
		//publicKeys.add(signBankMsg.getPublicKeyVendor());
		//publicKeys.add(signBankMsg.getPublicKeyBank());
		//return publicKeys;


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
	public static byte[] hash(byte[] data) {
		Sha256Hash sha256 = new Sha256Hash();
		byte[] b = sha256.hash(data);
		return b;
	}

}
