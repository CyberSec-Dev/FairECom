import method.AttestVerify;
import utils.Sha256Hash;
import utils.query;

import javax.crypto.Cipher;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Mytest {
    public static void main(String[] args) throws Exception {
        //System.out.println(new BigInteger(hash("aaa".getBytes())).toString(16));
        //512
		String serviceId="aca2eb7d00ea1a7b8ebd4e68314663af";
		Double price=69.9;
//		String tid="00a870c6c06346e85335524935c600c0";

        //256
        //String serviceId="154e7e31ebfa092203795c972e5804a6";
	//Double price=23.99;
//		String tid="041cba819a99569f87996b65b73ea82e";

       // 128
       // Double price=19.99;
        //String tid="05bb540accb9a3fc966280b894023c9c";


        //64
      // String serviceId="437c05a395e9e47f9762e677a7068ce7";
	//	Double price=47.65;
//		String tid="0199115a1cbfc272c5bd53117772a64a";

        //8
		//Double price=53.79;
//		String tid="0b87d9e59c7c19b041fe36e77aa33a42";

        //4
        //Double price=53.5;
        //String tid="28e2f8073d78153ccf2053de91fc4db3";

        //2
        //Double price=50.21;
        //String tid="00b8d354b36820e9d6131fd5173c5581";

        //32
        //String serviceId="99a4788cb24856965c36a24e339b6058";
		//Double price=79.9;
//		String tid="00c763284c0056eed753352f5559ff0a";

       // Double price=89.9;
      //  String tid="01be661b8196707ef60f062632d6d1bd";

//       16
		//Double price=74.0;
//		String tid="0006ec9db01a64e59a68b2c340bf65a7";
      //  query.getPriceRoot(serviceId, price);
//        long start1=System.nanoTime();
//        List<String> a=query.getList("99a4788cb24856965c36a24e339b6058",79.9);
//        long cost1=System.nanoTime()-start1;
//        System.out.println("从数据库读取交易的时间花销：" + cost1 / 1000 / 1000 + "ms");
        long cost=0;
        for(int i=0;i<50;i++) {
            long start2 = System.nanoTime();
            //读取list_order表
            List<String> lists= query.getList(serviceId, price);
            //读取t_manager表
            // List<String> lists= query.getTransactionId(serviceId, price);
            long cost2 = System.nanoTime() - start2;
            cost=cost+cost2;
            System.out.println("从数据库读取交易的时间花销：" + cost2 / 1000 / 1000 + "ms");
          //  System.out.println(lists.size());
      //  System.out.println("读取数据个数：" + lists.size());
        }
        System.out.println("从数据库读取交易的平均时间花销：" + cost / 1000 / 1000/50 + "ms");









//
//        long cost=0;
//        byte[] a = "abc".getBytes();
//        byte[] b = "abcd".getBytes();
//        for(int j=0;j<100;j++) {
//            long start6=System.nanoTime();
//           Sha256Hash.hash(Integer.toString(j).getBytes());
////            Random rd = new Random();
////            int m = rd.nextInt(4);
//            long cost6=System.nanoTime()-start6;
//if(j!=0) {
//    cost = cost + cost6;
//}
//           // System.out.println("sha256 1的时间花销："+cost6/1000+"us");
//
//        }
//         System.out.println("sha256："+cost/99/1000+"us");
//        Random rd = new Random();
//        int m = rd.nextInt(100);
//        long costG=0;
//        long costR=0;
//        long costD=0;
//        for (int i = 0; i < 5; i++){
//            long start1=System.nanoTime();
//            int RSAkeylen = 2048;
//        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
//        kpg.initialize(RSAkeylen);
//        KeyPair kp = kpg.generateKeyPair();
//        // 公钥
//        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
//        // 私钥
//        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
//        long cost1=System.nanoTime()-start1;
//        costG=costG+cost1;
//            System.out.println("密钥生成的时间花销："+cost1/1000/1000+"ms");
//
//            long start2=System.nanoTime();
//            byte[] encode = RSA(privateKey, a);
//            long cost2=System.nanoTime()-start2;
//            costR=costR+cost2;
//            System.out.println("签名的时间花销："+cost2/1000+"us");
//
//            long start3=System.nanoTime();
//            byte[] decode = DERSA(publicKey, encode);
//            long cost3=System.nanoTime()-start3;
//            costD=costD+cost3;
//           System.out.println("验证的时间花销："+cost3/1000+"us");
//    }
//         System.out.println("密钥生成的时间花销："+costG/1000/1000/5+"ms");
//         System.out.println("签名的时间花销："+costR/1000/5+"us");
//         System.out.println("验证的时间花销："+costD/1000/5+"us");

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
