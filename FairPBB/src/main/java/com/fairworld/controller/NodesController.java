package com.fairworld.controller;

import com.fairworld.pojo.Nodes;
import com.fairworld.pojo.Price;
import com.fairworld.pojo.list_order;
import com.fairworld.pojo.node;
import com.fairworld.service.ManagerImpl;
import com.fairworld.service.NodeImpl;
import com.fairworld.service.NodesImpl;
import com.fairworld.service.list_order_Impl;
import com.fairworld.utils.MerkleTree;
import com.fairworld.utils.ethStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import javax.xml.soap.SAAJResult;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.fairworld.utils.Utils.removeDuplicate;

@Controller
public class NodesController {

    @Autowired
    NodesImpl nodesImpl;
    @Autowired
    list_order_Impl listOrder;
    @Autowired
    ManagerImpl manager;
    @Autowired
    NodeImpl node;

    @RequestMapping("/test01")
    @ResponseBody
    public void test01(){
        List<com.fairworld.pojo.node> nodes = node.IfExistProduct("99a4788cb24856965c36a24e339b6058");
        System.out.println(nodes);
    }

    @RequestMapping("/nodes")
    public String  getNodes(String id, Model mode) throws NoSuchAlgorithmException, IOException {
        List<node> nodeList = node.IfExistProduct(id);
        List<String> priceRoot = new ArrayList<String >();
        List<Nodes> nodes = new ArrayList<Nodes>();
        String ProductId = nodeList.get(0).getProductId();
        String address = nodeList.get(0).getContractHash();
        int listNumber = nodeList.size();
        int customer = 0;
        for (node node1 : nodeList) {
            customer = node1.getNumber() + customer;
            Nodes node01 = new Nodes();
            node01.setPrice(node1.getPrice());
            String priceroot = ethStorage.getPrice(address,node1.getStrIndex());
            node01.setPriceRoot(priceroot);
            priceRoot.add(priceroot);
            node01.setContractHash(node1.getContractHash());
            node01.setTransactionHash(node1.getTransactionHash());
            node01.setProductId(ProductId);
            node01.setId(node1.getStrIndex());
            node01.setNumber(node1.getNumber());
            nodes.add(node01);
        }
        MerkleTree merkleTree = MerkleTree.merkleTree(priceRoot);
        String roothash = new BigInteger(1,merkleTree.getHash()).toString(16);
        mode.addAttribute("nodes",nodes);
        mode.addAttribute("address",address);
        mode.addAttribute("customer",customer);
        mode.addAttribute("productId",ProductId);
        mode.addAttribute("pricenumber",listNumber);
        mode.addAttribute("roothash",roothash);
        return "nodelist";
//        List<Nodes> nodes = nodesImpl.getNodesById(id);
//        String ProductId = nodes.get(0).getProductId();
//        String address = nodes.get(1).getContractHash();
//        int listNumber = ethStorage.getListNumber(address);
//        int customer = 0;
//        List<Double> pricelist = new ArrayList<Double>();
//        for (int i = 1; i <= listNumber; i++) {
//            long b= System.currentTimeMillis();
//            String priceRoot = ethStorage.getPrice(address,i);
//            nodes.get(i-1).setId(i);
//            nodes.get(i-1).setPriceRoot(priceRoot);
//            pricelist.add(nodes.get(i-1).getPrice());
//            customer = customer + nodes.get(i-1).getNumber();
//        }
//        removeDuplicate(pricelist);
//        mode.addAttribute("nodes",nodes);
//        mode.addAttribute("address",address);
//        mode.addAttribute("customer",customer);
//        mode.addAttribute("productId",ProductId);
//        mode.addAttribute("pricenumber",pricelist.size());
//        mode.addAttribute("roothash",nodes.get(0).getRoot());

    }

    @RequestMapping("/order")
    public String getOrders(double price,String productId,String priceRoot,String txHash,Model model){

        List<list_order> orders = listOrder.getOrderByPrice(productId, price);
        int i = 1;
        for (list_order order : orders) {
            order.setId(i);
            i++;
        }
        model.addAttribute("orders",orders);
        model.addAttribute("ProductID",productId);
        model.addAttribute("CustomerNumber",orders.size());
        model.addAttribute("Price",price);
        model.addAttribute("PriceRoot",priceRoot);
        model.addAttribute("txHash",txHash);
        return "orderlist";
    }
//    @RequestMapping("/test03")
//    @ResponseBody
//    public Nodes getOrders01(){
//        Nodes nodeByRoot = nodesImpl.getNodeByRoot("=============49.9=============");
//        System.out.println(nodeByRoot);
//        String id = nodeByRoot.getProductId();
//        double price = nodeByRoot.getPrice();
//        List<list_order> orders = listOrder.getOrderByPrice(id, price);
//        int i = 1;
//        for (list_order order : orders) {
//            order.setId(i);
//            i++;
//        }
//        System.out.println(orders);
//        return nodeByRoot;
//    }



}
