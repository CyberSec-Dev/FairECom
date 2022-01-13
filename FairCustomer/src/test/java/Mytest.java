import msg.RSAKey1;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

public class Mytest {
    public static void main(String[] args) throws Exception {
        // RSA
        ArrayList<RSAKey1> keys=new ArrayList<>();
        int RSAkeylen = 2048;
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(RSAkeylen);
        KeyPair kp = kpg.generateKeyPair();

        RSAPrivateKey privateKey1 = (RSAPrivateKey) kp.getPrivate();
        RSAPublicKey publicKey1 = (RSAPublicKey) kp.getPublic();
        RSAKey1 key1=new RSAKey1("customer",privateKey1,publicKey1);
        System.out.println(privateKey1);
        RSAPrivateKey privateKey2 = (RSAPrivateKey) kp.getPrivate();
        RSAPublicKey publicKey2 = (RSAPublicKey) kp.getPublic();
        RSAKey1 key2=new RSAKey1("vendor",privateKey2,publicKey2);



        RSAPrivateKey privateKey4 = (RSAPrivateKey) kp.getPrivate();
        RSAPublicKey publicKey4 = (RSAPublicKey) kp.getPublic();
        RSAKey1 key4=new RSAKey1("bank",privateKey4,publicKey4);

        keys.add(key1);
        keys.add(key2);
      //  keys.add(key3);
        keys.add(key4);
        FileOutputStream fout=new FileOutputStream("./RSAKey");
        ObjectOutputStream out=new ObjectOutputStream(fout);
        out.writeObject(keys);

//        FileInputStream fin=new FileInputStream("./RSAKey");
//        ObjectInputStream in=new ObjectInputStream(fin);
//        ArrayList<RSAKey1> keys2=(ArrayList<RSAKey1>) in.readObject();
//        System.out.println(keys2.get(0).getUsername());
//        System.out.println(keys2.get(0).getPrivateKey());
//
//        RSAKey1 a=keys2.get(1);
//        keys2.set(1,new RSAKey1(a.getUsername(),a.getPublicKey()));
//        RSAKey1 b=keys2.get(2);
//        keys2.set(2,new RSAKey1(b.getUsername(),b.getPublicKey()));
//        RSAKey1 c=keys2.get(3);
//        keys2.set(3,new RSAKey1(c.getUsername(),c.getPublicKey()));
//
//
//                FileOutputStream fout=new FileOutputStream("./RSAKey");
//       ObjectOutputStream out=new ObjectOutputStream(fout);
//        out.writeObject(keys2);

//        FileInputStream fin1=new FileInputStream("./RSAKey");
//        ObjectInputStream in1=new ObjectInputStream(fin1);
//        ArrayList<RSAKey1> keys3=(ArrayList<RSAKey1>) in1.readObject();
//        System.out.println(keys3.get(1).getUsername());
//        System.out.println(keys3.get(1).getPrivateKey());

    }
    /**
     * Gi映射到Hi,RSA签名
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
     * RSA解密
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
