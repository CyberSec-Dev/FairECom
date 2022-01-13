import contract.Storage;
import dao.NodeMapper;
import org.apache.ibatis.session.SqlSession;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import pojo.node;
import utils.MybatisUtils;
import utils.Nodes;
import utils.ethStorage;
import utils.query;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class test {
    private static String url = "https://rinkeby.infura.io/v3/34377eeeb81a4e87932af7b5b84296f1";
    private static Web3j web3j = Web3j.build(new HttpService(url));
    private static String password = "huangran123";
    private static String source = "./src/main/resources/UTC--2021-11-09T01-28-25.520Z--a014671b7a14c4da5b09d42668e56b2b9751f1d6";

    public static void main(String[] args) {
//        node node = new node();
//        node.setContractHash("123");
//        node.setNumber(12);
//        node.setPrice(12.0);
//        node.setProductId("1200");
//        node.setTransactionHash("1111111111111");
//        node.setStrIndex(utils.Nodes.getIndex("123"));
//        Nodes.insertNode(node);



//        SqlSession session = MybatisUtils.getSession();
//        NodeMapper mapper = session.getMapper(NodeMapper.class);
//        List<node> nodes = new ArrayList<node>();
//        nodes = mapper.getPriceRoot("99a4788cb24856965c36a24e339b6058",74);
//        String contractHash = nodes.get(0).getContractHash();
//        int strIndex = nodes.get(0).getStrIndex();
//        session.close();
//        String price = ethStorage.getPrice(contractHash, strIndex);
//        System.out.println(price);
//        System.out.println(nodes);

        Credentials credentials  = null;
        try {
            credentials = WalletUtils.loadCredentials(password,source);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        Storage product = Storage.load("0xc98704a040e25cf167baa97687d51394a9629d68",web3j,credentials, Contract.GAS_PRICE,Contract.GAS_LIMIT);
        String priceroot= null;
        try {
            priceroot= product.getPriceRoot(BigInteger.valueOf(1)).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(priceroot);
    }
}
