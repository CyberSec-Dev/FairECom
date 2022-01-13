import msg.RSAKey1;
import pojo.node;
import utils.Nodes;
import utils.ethStorage;

import java.io.*;
import java.util.ArrayList;

public class test {


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        FileInputStream fin=new FileInputStream("./RSAKey");
        ObjectInputStream in=new ObjectInputStream(fin);
        ArrayList<RSAKey1> keys2=(ArrayList<RSAKey1>) in.readObject();
        System.out.println(keys2.get(0).getUsername());
        System.out.println(keys2.get(0).getPrivateKey());

        RSAKey1 a=keys2.get(0);
        keys2.set(0,new RSAKey1(a.getUsername(),a.getPublicKey()));
        RSAKey1 b=keys2.get(1);
        keys2.set(1,new RSAKey1(b.getUsername(),b.getPublicKey()));
        RSAKey1 c=keys2.get(2);
        keys2.set(2,new RSAKey1(c.getUsername(),c.getPublicKey()));


        FileOutputStream fout=new FileOutputStream("./managerRSAKey");
        ObjectOutputStream out=new ObjectOutputStream(fout);
        out.writeObject(keys2);


    }

}
