package utils;


import dao.ManagerMapper;
import dao.NodeMapper;
import dao.NodesDao;
import dao.list_orderMapper;
import msg.Transaction;
import msg.TransactionSign;
import org.apache.ibatis.session.SqlSession;
import pojo.list_order;
import pojo.node;
import pojo.t_manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class query {
    public static String getPriceRoot(String productId, double price) {
//       // long start=System.nanoTime();
//        SqlSession session = MybatisUtils.getSession();
//        NodesDao mapper = session.getMapper(NodesDao.class);
//        Nodes nodes = mapper.getNodes(productId, price);
//        String priceroot01 = nodes.getPriceRoot();
//       // long cost=System.nanoTime()-start;
//
//
//        String address = nodes.getContractHash();
//        int listNumber = ethStorage.getListNumber(address);
//        String priceRoot = null;
//        for (int i = 1; i <= listNumber; i++) {
//            if (priceroot01.equals(ethStorage.getPrice(address, i))) {
//                priceRoot = ethStorage.getPrice(address, i);
//            }
//        }
//        if (priceRoot == null) {
//            return "Ethereum is empty";
//        } else {
//            return priceRoot;
//        }
        SqlSession session = MybatisUtils.getSession();
        NodeMapper mapper = session.getMapper(NodeMapper.class);
        List<node> nodes = new ArrayList<node>();
        nodes = mapper.getPriceRoot(productId,price);
        String contractHash = nodes.get(0).getContractHash();
        int strIndex = nodes.get(0).getStrIndex();
        session.close();
        return ethStorage.getPrice(contractHash,strIndex);
    }

    public static Map<Double, List<Transaction>> getList_order(String productId) {
        SqlSession session = MybatisUtils.getSession();
        list_orderMapper mapper = session.getMapper(list_orderMapper.class);
        List<list_order> orders = mapper.getList_order(productId);
        List<Double> prices = new ArrayList<Double>();
        for (list_order order : orders) {
            prices.add(order.getPrice());
        }
        prices = Utils.removeDuplicate(prices);
        Map<Double, List<Transaction>> priceMap = new HashMap<Double, List<Transaction>>();
        for (double price : prices) {
            List<Transaction> lists = new ArrayList<Transaction>();
            int i = 0;
            for (list_order order : orders) {
                if (price == order.getPrice()) {
                    lists.add(new Transaction(order.getOrderId(),order.getProductId(),order.getSellerId(),order.getPrice()));
                }
                priceMap.put(price, lists);
            }

        }
        return priceMap;
    }
    public static List<String> getList(String productId, double price){
       // long start =System.nanoTime();
        SqlSession session = MybatisUtils.getSession();
        list_orderMapper Mapper = session.getMapper(list_orderMapper.class);
        List<list_order> manager = Mapper.getList(productId, price);
        List<String> lists = new ArrayList<String>();
        for (list_order list_order : manager) {
           lists.add(list_order.getOrderId());
        }
        session.close();
        return lists;
    }
    public static list_order getOrder(String orderId){
        // long start =System.nanoTime();
        SqlSession session = MybatisUtils.getSession();
        list_orderMapper Mapper = session.getMapper(list_orderMapper.class);
       list_order manager = Mapper.getOrder(orderId);

        session.close();
        return manager;
    }
    public static List<String> getTransactionId(String productId,double price){
        SqlSession session = MybatisUtils.getSession();
        ManagerMapper managerMapper = session.getMapper(ManagerMapper.class);
        List<t_manager> manager = managerMapper.getManager(productId, price);
        List<String> transactions = new ArrayList<String>();
        for (t_manager t_manager : manager) {
            transactions.add(t_manager.getTransactionId());

        }
        return transactions;

    }
}
