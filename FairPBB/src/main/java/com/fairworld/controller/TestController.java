package com.fairworld.controller;

import com.fairworld.mapper.list_order_mapper;
import com.fairworld.pojo.Nodes;
import com.fairworld.pojo.list_order;
import com.fairworld.pojo.t_manager;
import com.fairworld.service.ManagerImpl;
import com.fairworld.service.NodesImpl;
import com.fairworld.utils.MerkleTree;
import com.fairworld.utils.Utils;
import com.fairworld.utils.ethStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
public class TestController {

    @Autowired
    list_order_mapper listOrderMapper;
    @Autowired
    NodesImpl nodesImpl;
    @Autowired
    ManagerImpl manager;

    @GetMapping("/get/{id}")
    public List<list_order> get(@PathVariable("id") String id){
        return listOrderMapper.getOrderById(id);
    }

    @RequestMapping("/test01/{id}")
    public void test(@PathVariable("id") String id) throws NoSuchAlgorithmException, IOException {
        List<list_order> orders = listOrderMapper.getOrderById(id);
        List<Double> prices = new ArrayList<Double>();
        for (list_order order : orders) {
            prices.add(order.getPrice());
        }
        prices = Utils.removeDuplicate(prices);
        Map<Double,List<String>> priceMap = new HashMap<Double, List<String>>();
        for (double price : prices) {
            List<String> lists = new ArrayList<String>();
            int i = 0;
            for (list_order order : orders) {
                if (price == order.getPrice()){
                    lists.add(order.getOrderId());
                    t_manager tManager = new t_manager();
                    tManager.setTransactionId(order.getOrderId());
                    tManager.setPrice(price);
                    tManager.setPosition(i++);
                    tManager.setServiceId(order.getProductId());
                    tManager.setVendorId(order.getSellerId());
                    manager.insertManager(tManager);
                }
                priceMap.put(price,lists);
            }
        }
        int i = 1;
        /*
        priceMap
         */
        List<String> hashlist = new ArrayList<String>();
        for (Double price : prices) {
            MerkleTree merkleTree01 = MerkleTree.merkleTree(priceMap.get(price));
            System.out.println(priceMap.get(price));
            String priceroot = new BigInteger(1,merkleTree01.acc()).toString(16);
            System.out.println("priceroot"+priceroot);
            hashlist.add(priceroot);
        }
        MerkleTree merkleTree = MerkleTree.merkleTree(hashlist);
        String root = new BigInteger(1,merkleTree.getHash()).toString(16);
        String address = ethStorage.deploy();
        ethStorage.init(address,id);

        long a= System.currentTimeMillis();//获取当前系统时间(毫秒)
        for (Double price : prices) {
            MerkleTree merkleTree02 = MerkleTree.merkleTree(priceMap.get(price));
            String priceRoot = new BigInteger(1,merkleTree02.acc()).toString(16);
            String txHash = ethStorage.addPriceRoot(address,priceRoot);
            System.out.println(txHash);
            int size = priceMap.get(price).size();
            Nodes nodes = new Nodes();
            nodes.setProductId(id);
            nodes.setPrice(price);
            nodes.setRoot(root);
            nodes.setPriceRoot(priceRoot);
            nodes.setTransactionHash(txHash);
            nodes.setContractHash(address);
            nodes.setNumber(size);
            nodesImpl.insertNodes(nodes);
        }
//        System.out.println(prices);
//        System.out.println(priceMap);
        System.out.println((System.currentTimeMillis()-a)/1000 + "秒");
        System.out.println(prices.size()+2);
    }
    @RequestMapping("/test05")
    public void list(){
        t_manager tManager = new t_manager();
        tManager.setTransactionId("11111111111111");
        manager.insertManager(tManager);
    }
}
