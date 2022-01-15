package method;



import msg.*;
import pojo.node;
import pojo.t_manager;
import utils.MerkleTree;
import utils.Nodes;
import utils.ethStorage;
import utils.query;
import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Manager start!");
        int managerPort=8086;
        ServerSocket socket=new ServerSocket(managerPort);
        FileInputStream fin=new FileInputStream("./managerRSAKey");
        ObjectInputStream in=new ObjectInputStream(fin);
        ArrayList<RSAKey1> keys=(ArrayList<RSAKey1>) in.readObject();
        RSAPublicKey publicKeyC=keys.get(0).getPublicKey();
        RSAPublicKey publicKeyB=keys.get(2).getPublicKey();
        RSAPublicKey publicKeyV=keys.get(1).getPublicKey();
        while(true) {

        Socket s=socket.accept();
           // System.out.println("accept");
        ObjectOutputStream oosManager = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream oisManager= new ObjectInputStream(s.getInputStream());
            Object object = oisManager.readObject();
            if (object instanceof SignVendorMsg) {
                SignVendorMsg signVendorMsg = (SignVendorMsg) object;
                //SignVendorMsg signVendorMsg=(SignVendorMsg) oisManager.readObject();
                ArrayList<byte[]> msgc = signVendorMsg.getMsg();
                ArrayList<byte[]> signc = signVendorMsg.getSignClient();
               // RSAPublicKey publicKeyC = signVendorMsg.getPublicKeyClient();
                ArrayList<byte[]> signv1 = signVendorMsg.getSignVendor1();
                ArrayList<byte[]> signv2 = signVendorMsg.getSignVendor2();
               // RSAPublicKey publicKeyV = signVendorMsg.getPublicKeyVendor();
                for (int i = 0; i < signc.size(); i++) {
                    byte[] decrypted = DERSA(publicKeyC, signc.get(i));
                    String[] array=new String(msgc.get(i)).split(",");
                    if (new BigInteger(decrypted).equals(new BigInteger(msgc.get(i)))) {
                       System.out.println("C's sig. verf. success. tid="+array[0]);
                    } else {
                        System.out.println("C's sig. verf. fail. tid="+array[0]);
                    }
                    byte[] decrptedv1 = DERSA(publicKeyV, signv1.get(i));
                    byte[] decrptedv2 = DERSA(publicKeyV, signv2.get(i));
                   // String[] array = new String(msgc.get(i)).split(",");
                    String msg1 = array[0] + "," + array[1] + "," + array[3] + "," + array[4] + "," + array[5];
                    if (new BigInteger(decrptedv1).equals(new BigInteger(msgc.get(i))) && new BigInteger(decrptedv2).equals(new BigInteger(msg1.getBytes()))) {
                        System.out.println("V's sig. verf. success. tid="+array[0]);
                    } else {
                        System.out.println("V's sig. verf. fail. tid="+array[0]);
                    }
                }
                ArrayList<Integer> positions = new ArrayList<>();
                for (int i = 0; i < signc.size(); i++) {
                    positions.add(i);
                }
                oosManager.writeObject(positions);
                SignBankMsg signBankMsg = (SignBankMsg) oisManager.readObject();
                ArrayList<byte[]> msgv = signBankMsg.getMsg();
                ArrayList<byte[]> signv11 = signBankMsg.getSignVendor1();
                ArrayList<byte[]> signv21 = signBankMsg.getSignVendor2();
               // RSAPublicKey publicKeyV1 = signBankMsg.getPublicKeyVendor();
                ArrayList<byte[]> signb1 = signBankMsg.getSignBank1();
                ArrayList<byte[]> signb2 = signBankMsg.getSignBank2();
                //RSAPublicKey publicKeyb = signBankMsg.getPublicKeyBank();
                for (int i = 0; i < msgv.size(); i++) {
                    String[] array = new String(msgv.get(i)).split(",");
                    String msg1 = array[0] + "," + array[1] + "," + array[3] + "," + array[4] + "," + array[5] + "," + array[6];
                    byte[] decrypted = DERSA(publicKeyV, signv11.get(i));
                    byte[] decrypted1 = DERSA(publicKeyV, signv21.get(i));
                    if (new BigInteger(decrypted).equals(new BigInteger(msgv.get(i))) && new BigInteger(decrypted1).equals(new BigInteger(msg1.getBytes()))) {
                        System.out.println("V's sig. verf. success. tid="+array[0]);
                    } else {
                        System.out.println("V's sig. verf. fail. tid="+array[0]);
                    }
                    byte[] decrptedb1 = DERSA(publicKeyB, signb1.get(i));
                    byte[] decrptedb2 = DERSA(publicKeyB, signb2.get(i));
                    if (new BigInteger(decrptedb1).equals(new BigInteger(msgv.get(i))) && new BigInteger(decrptedb2).equals(new BigInteger(msg1.getBytes()))) {
                        System.out.println("B's sig. verf. success. tid="+array[0]);
                    } else {
                        System.out.println("B's sig. verf. fail. tid="+array[0]);
                    }
                }
                ArrayList<t_manager> trans = new ArrayList<>();
                for (int i = 0; i < msgv.size(); i++) {
                    String[] array = new String(msgv.get(i)).split(",");
                    t_manager tran = new t_manager(array[0], array[1], array[3], array[4], Double.parseDouble(array[5]), Integer.parseInt(array[6]), signv21.get(i), signb2.get(i));
                    trans.add(tran);
                }
                query.insertTransactions(trans);
                List<String> lists = new ArrayList<>();
                for (int i = 0; i < trans.size(); i++) {
                    lists.add(trans.get(i).getTransactionId());
                }
                //某个productId某个price的Merkle Tree
                MerkleTree merkle = MerkleTree.merkleTree(lists);
                System.out.println("MTRoot=" + new BigInteger(1,merkle.getHash()).toString(16));
                String acc = new BigInteger(1,merkle.acc()).toString(16);
                System.out.println("acc="+new BigInteger(1,merkle.acc()).toString(16));
                String productId=trans.get(0).getServiceId();
                double price=trans.get(0).getPrice();
                int number=trans.size();

                String contractAddress = null;
                if ( Nodes.IfExist(productId)){
                    contractAddress = ethStorage.deploy();          //Determine whether the product already exists in the database, and if not, create a smart contract
                } else {
                    contractAddress = Nodes.getContractAddress(productId);
                }
                String txHash = ethStorage.addPriceRoot(contractAddress,acc);       //The root  in Ethereum
                //node database
                node node = new node();
                node.setProductId(productId);
                node.setPrice(price);
                node.setContractHash(contractAddress);
                node.setNumber(number);
                node.setTransactionHash(txHash);
                node.setStrIndex(Nodes.getIndex(productId));
                Nodes.insertNode(node);
                File file=new File("./merkle/merkle" +"-"+ trans.get(0).getServiceId());
                if (!file .exists() && !file .isDirectory())
                {
                    file .mkdir();
                }
                FileOutputStream fout = new FileOutputStream("./merkle/merkle" +"-"+ trans.get(0).getServiceId() + "/"+trans.get(0).getPrice());
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(merkle);
                System.out.println("Store Merkle Tree.");
            } else if(object instanceof AttestmMsg){
               // String a = (String) object;
               // System.out.println(a);
               // AttestmMsg attestmMsg = (AttestmMsg) oisManager.readObject();
                AttestmMsg attestmMsg=(AttestmMsg)object;
                System.out.println("Proof of membership start.");
                System.out.println("Read Attestation.");
                FileInputStream fin1 = new FileInputStream("./merkle/merkle" +"-"+ attestmMsg.getServiceID() +"/"+ attestmMsg.getPrice());
                ObjectInputStream ois = new ObjectInputStream(fin1);
                MerkleTree merkle = (MerkleTree) ois.readObject();
                System.out.println("Read Merkle tree from file");
                int pos=merkle.findPosition(attestmMsg.getHash());
                ArrayList<SiblingsMsg> siblingsm = merkle.siblings(pos);
                ProvemMsg provemMsg=new ProvemMsg(siblingsm);
                oosManager.writeObject(provemMsg);
                oosManager.close();
            }else if(object instanceof AttestcMsg){
                AttestcMsg attestcMsg=(AttestcMsg)object;
                System.out.println("Proof of cardinality start.");
                System.out.println("Read Attestation.");
                 ArrayList<Integer> rands = attestcMsg.getRands();
                FileInputStream fin1 = new FileInputStream("./merkle/merkle" +"-"+ attestcMsg.getServiceID() +"/"+ attestcMsg.getPrice());
                ObjectInputStream ois = new ObjectInputStream(fin1);
                MerkleTree merkle = (MerkleTree) ois.readObject();
                System.out.println("Read Merkle tree from file.");
                LastLeaf lastLeaf = merkle.findLastLeaf();
                ArrayList<SiblingsMsg> siblings = merkle.siblings(lastLeaf.getPos());
                ArrayList<byte[]> randLeaves = new ArrayList<>();
                ArrayList<ArrayList<SiblingsMsg>> randLeavesSiblings = new ArrayList<ArrayList<SiblingsMsg>>();
                for (int i = 0; i < rands.size(); i++) {
                    byte[] leaf = merkle.findLeaf(rands.get(i));
                    randLeaves.add(leaf);
                    ArrayList<SiblingsMsg> sibs = merkle.siblings(rands.get(i));
                    randLeavesSiblings.add(sibs);
                }
                ArrayList<TransactionSign> transactionsRand=new ArrayList<>();
                ArrayList<TransactionSign> transactions = query.getTransactions(attestcMsg.getServiceID(), attestcMsg.getPrice());
                for(int i=0;i<rands.size();i++){
                    transactionsRand.add(transactions.get(rands.get(i)));
                }
                ProveMsg proveMsg = new ProveMsg(lastLeaf.getPos() + 1, lastLeaf.getHash(), siblings, randLeaves, randLeavesSiblings, transactionsRand);
                oosManager.writeObject(proveMsg);
                System.out.println("Write proof to client.");

            }
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
