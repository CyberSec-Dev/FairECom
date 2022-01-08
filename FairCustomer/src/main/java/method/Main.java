package method;
import method.AttestVerify;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
          System.out.println("Client start!");
          Socket socketVendor = new Socket("127.0.0.1", 8089);
          if (socketVendor == null)
                    return;
          ObjectOutputStream oosVendor = new ObjectOutputStream(socketVendor.getOutputStream());
          ObjectInputStream oisVendor = new ObjectInputStream(socketVendor.getInputStream());

          OrderFulfill orderFulfill = new OrderFulfill();
          ArrayList<RSAPublicKey> publickeys = orderFulfill.orderFulfill(oosVendor, oisVendor);
          oosVendor.close();
          oisVendor.close();

          AttestVerify attestVerify = new AttestVerify();
          attestVerify.attestVerify(publickeys);
            }


}
