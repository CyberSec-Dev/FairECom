package method;


import msg.SignBankMsg;
import msg.SignClientMsg;
import msg.SignVendorMsg;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Vendor start!");
        int RSAkeylen = 2048;
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(RSAkeylen);
        KeyPair kp = kpg.generateKeyPair();
        RSAPrivateKey privateKeyV = (RSAPrivateKey) kp.getPrivate();
        RSAPublicKey publicKeyV = (RSAPublicKey) kp.getPublic();
        ServerSocket socket=new ServerSocket(8089);
        while(true) {
            Socket s = socket.accept();
            ObjectOutputStream oosClient = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream oisClient = new ObjectInputStream(s.getInputStream());
            SignClientMsg signClientMsg = (SignClientMsg) oisClient.readObject();
            ArrayList<byte[]> msgc = signClientMsg.getMsg();
            ArrayList<byte[]> signc = signClientMsg.getSignMsg();
            RSAPublicKey publicKeyC = signClientMsg.getPublicKey();
            for (int i = 0; i < signc.size(); i++) {
                byte[] decrypted = DERSA(publicKeyC, signc.get(i));
                String[] array=new String(msgc.get(i)).split(",");
                if (new BigInteger(decrypted).equals(new BigInteger(msgc.get(i)))) {
                     System.out.println("C's sig. verf. success. tid="+array[0]);
                } else {
                    System.out.println("C's sig. verf. fail. tid="+array[0]);
                }
            }

            ArrayList<byte[]> signV1 = new ArrayList<>();
            ArrayList<byte[]> signV2 = new ArrayList<>();
            for (int i = 0; i < msgc.size(); i++) {
                byte[] sign = RSA(privateKeyV, msgc.get(i));
                signV1.add(sign);
                String[] array = new String(msgc.get(i)).split(",");
                String msg1 = array[0] + "," + array[1] + "," + array[3] + "," + array[4] + "," + array[5];
                byte[] sign1 = RSA(privateKeyV, msg1.getBytes());
                signV2.add(sign1);
            }
            SignVendorMsg signV = new SignVendorMsg(msgc, signc, publicKeyC, signV1, signV2, publicKeyV);
            Socket socketVendor = new Socket("127.0.0.1", 8086);
            if (socketVendor == null)
                return;
            ObjectOutputStream oosManager = new ObjectOutputStream(socketVendor.getOutputStream());
            ObjectInputStream oisManager = new ObjectInputStream(socketVendor.getInputStream());
            oosManager.writeObject(signV);

            ArrayList<Integer> positions = (ArrayList<Integer>) oisManager.readObject();
            ArrayList<byte[]> msgv = new ArrayList<>();
            for (int i = 0; i < msgc.size(); i++) {
                String msg = new String(msgc.get(i)) + "," + Integer.toString(positions.get(i));
                msgv.add(msg.getBytes());
            }
            ArrayList<byte[]> signV11 = new ArrayList<>();
            ArrayList<byte[]> signV22 = new ArrayList<>();
            for (int i = 0; i < msgv.size(); i++) {
                byte[] sign = RSA(privateKeyV, msgv.get(i));
                signV11.add(sign);
                String[] array = new String(msgv.get(i)).split(",");
                String msg1 = array[0] + "," + array[1] + "," + array[3] + "," + array[4] + "," + array[5] + "," + array[6];
                byte[] sign1 = RSA(privateKeyV, msg1.getBytes());
                signV22.add(sign1);
            }
            SignVendorMsg signv = new SignVendorMsg(msgv, signc, publicKeyC, signV11, signV22, publicKeyV);
            Socket socketBank = new Socket("127.0.0.1", 8087);
            if (socketVendor == null)
                return;
            ObjectOutputStream oosBank = new ObjectOutputStream(socketBank.getOutputStream());
            ObjectInputStream oisBank = new ObjectInputStream(socketBank.getInputStream());
            oosBank.writeObject(signv);
            SignBankMsg signBankMsg = (SignBankMsg) oisBank.readObject();
            ArrayList<byte[]> msgv1 = signBankMsg.getMsg();
            ArrayList<byte[]> signB1 = signBankMsg.getSignBank1();
            ArrayList<byte[]> signB2 = signBankMsg.getSignBank2();
            RSAPublicKey publicKeyB = signBankMsg.getPublicKeyBank();
            for (int i = 0; i < msgv1.size(); i++) {
                byte[] decrypted = DERSA(publicKeyB, signB1.get(i));
                String[] array = new String(msgv1.get(i)).split(",");
                String msg1 = array[0] + "," + array[1] + "," + array[3] + "," + array[4] + "," + array[5] + "," + array[6];
                byte[] decrypted2 = DERSA(publicKeyB, signB2.get(i));
                if (new BigInteger(decrypted).equals(new BigInteger(msgv1.get(i))) && new BigInteger(decrypted2).equals(new BigInteger(msg1.getBytes()))) {
                   System.out.println("V's sig. verf. success. tid="+array[0]);
                } else {
                    System.out.println("V's sig. verf. fail. tid="+array[0]);
                }
            }
            oosManager.writeObject(signBankMsg);
            oosClient.writeObject(signBankMsg);
           // System.out.println("Vendor finish!");

        }








    }
    /**
     * RSAsign
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
