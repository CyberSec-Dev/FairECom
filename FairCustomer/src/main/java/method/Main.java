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
          System.out.println("Customer start!");
          String vendorIp="127.0.0.1";
          int  vendorPort=8089;
          String managerIp="127.0.0.1";
          int managerPort=8086;
          while(true){
              System.out.println("Please enter an action to perform ：1.Create transactions.  2.Verify transactions.  3.Quit" );
              Scanner scan =new Scanner(System.in);
              String s=scan.next();
              if(s.equals("1")) {
                  Socket socketVendor = new Socket(vendorIp, vendorPort);
                  if (socketVendor == null)
                      return;
                  ObjectOutputStream oosVendor = new ObjectOutputStream(socketVendor.getOutputStream());
                  ObjectInputStream oisVendor = new ObjectInputStream(socketVendor.getInputStream());

                  OrderFulfill orderFulfill = new OrderFulfill();
                  orderFulfill.orderFulfill(oosVendor, oisVendor);
                  oosVendor.close();
                  oisVendor.close();
              }else if(s.equals("2")) {
                  AttestVerify attestVerify = new AttestVerify();
                  attestVerify.attestVerify(managerIp,managerPort);
              }else{
                  break;
              }
            }
    }

}
