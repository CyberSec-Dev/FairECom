package method;

import msg.RSAKey1;
import msg.SignBankMsg;
import msg.SignVendorMsg;
import pojo.Transaction;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

public class Main {
    public static void main(String[] agrs) throws Exception {
        System.out.println("Bank start!");
        int bankPort=8087;
        ServerSocket socket=new ServerSocket(bankPort);
        FileInputStream fin=new FileInputStream("./bankRSAKey");
        ObjectInputStream in=new ObjectInputStream(fin);
        ArrayList<RSAKey1> keys=(ArrayList<RSAKey1>) in.readObject();
        RSAPublicKey publicKeyC=keys.get(0).getPublicKey();
        RSAPublicKey publicKeyB=keys.get(2).getPublicKey();
        RSAPrivateKey privateKeyB=keys.get(2).getPrivateKey();
        RSAPublicKey publicKeyV=keys.get(1).getPublicKey();
        while(true) {
            Socket s = socket.accept();
            ObjectOutputStream oosVendor = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream oisVendor = new ObjectInputStream(s.getInputStream());
            SignVendorMsg signVendorMsg = (SignVendorMsg) oisVendor.readObject();
            ArrayList<byte[]> msgv = signVendorMsg.getMsg();
            ArrayList<byte[]> signc = signVendorMsg.getSignClient();
          //  RSAPublicKey publicKeyC = signVendorMsg.getPublicKeyClient();
            ArrayList<byte[]> signv1 = signVendorMsg.getSignVendor1();
            ArrayList<byte[]> signv2 = signVendorMsg.getSignVendor2();
            //RSAPublicKey publicKeyV = signVendorMsg.getPublicKeyVendor();
            for (int i = 0; i < signc.size(); i++) {
                String[] array = new String(msgv.get(i)).split(",");
                String msg1 = array[0] + "," + array[1] + "," + array[2] + "," + array[3] + "," + array[4] + "," + array[5];
                byte[] decrypted = DERSA(publicKeyC, signc.get(i));
                if (new BigInteger(decrypted).equals(new BigInteger(msg1.getBytes()))) {
                    System.out.println("C's sig. verf. success. tid="+array[0]);
                } else {
                    System.out.println("C's sig. verf. fail. tid="+array[0]);
                }
                byte[] decrptedv1 = DERSA(publicKeyV, signv1.get(i));
                byte[] decrptedv2 = DERSA(publicKeyV, signv2.get(i));
                //String[] array1 = new String(msgv.get(i)).split(",");
                String msg11 = array[0] + "," + array[1] + "," + array[3] + "," + array[4] + "," + array[5] + "," + array[6];
                if (new BigInteger(decrptedv1).equals(new BigInteger(msgv.get(i))) && new BigInteger(decrptedv2).equals(new BigInteger(msg11.getBytes()))) {
                    System.out.println("V's sig. verf. success. tid="+array[0]);
                } else {
                    System.out.println("V's sig. verf. fail. tid="+array[0]);
                }
            }
            for(int i=0;i<msgv.size();i++){
                String[] array1 = new String(msgv.get(i)).split(",");
                if(query.getTransaction(array1[0])==null){
                    query.insertTransaction(new Transaction(array1[0]));
                }else{
                    System.out.println("This transaction already exist!");
                }
            }

            ArrayList<byte[]> signB1 = new ArrayList<>();
            ArrayList<byte[]> signB2 = new ArrayList<>();
            for (int i = 0; i < msgv.size(); i++) {
                byte[] sign = RSA(privateKeyB, msgv.get(i));
                signB1.add(sign);
                String[] array = new String(msgv.get(i)).split(",");
                String msg1 = array[0] + "," + array[1] + "," + array[3] + "," + array[4] + "," + array[5] + "," + array[6];
                byte[] sign1 = RSA(privateKeyB, msg1.getBytes());
                signB2.add(sign1);
            }
            SignBankMsg signb = new SignBankMsg(msgv, signc, signv1, signv2,  signB1, signB2);
            oosVendor.writeObject(signb);
           // System.out.println("Bank finish!");
        }
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
