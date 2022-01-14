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

        keys.add(key4);
        FileOutputStream fout=new FileOutputStream("./RSAKey");
        ObjectOutputStream out=new ObjectOutputStream(fout);
        out.writeObject(keys);
    }


}
