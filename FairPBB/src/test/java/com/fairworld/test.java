package com.fairworld;

import com.fairworld.contract.Storage;
import com.fairworld.pojo.t_manager;
import com.fairworld.service.ManagerImpl;
import com.fairworld.service.NodeImpl;
import com.fairworld.service.NodesImpl;
import com.fairworld.utils.MerkleTree;
import com.fairworld.utils.ethStorage;
import lombok.SneakyThrows;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class test {

    @Autowired
    ManagerImpl manager;

    @Autowired
    NodesImpl nodes;

    @Autowired
    NodeImpl node;

    private static String url = "https://rinkeby.infura.io/v3/34377eeeb81a4e87932af7b5b84296f1";
    private static String password = "huangran123";
    String source = GetResource.class.getClassLoader().getResource("wallet/UTC--2021-11-09T01-28-25.520Z--a014671b7a14c4da5b09d42668e56b2b9751f1d6").getPath();


    @Test
    public void test02(){
        String id = "99a4788cb24856965c36a24e339b6058";

        int number = 33;
        List<String> hash = new ArrayList<String>();
        for (int i = 0; i < number; i++) {
            long a = System.currentTimeMillis();//获取当前系统时间(毫秒)
            String price = ethStorage.getPrice("0xb0007605e9def82e699f9a6db98fe3e3aea2932d", i);
            long b = System.currentTimeMillis();
            System.out.println(b-a);
        }

    }
    @SneakyThrows
    @Test
    public void test03(){
        List<String> list = new ArrayList<String>();
        list.add("11111111111111111111");
        list.add("222222222222222222222");
        list.add("33333333333333333333333");
        list.add("4444444444444444444444444444");
        list.add("55555555555555555555555555");
        MerkleTree merkleTree = MerkleTree.merkleTree(list);
        System.out.println(merkleTree.getHash());
        String root = new BigInteger(merkleTree.getHash()).toString();
        System.out.println(root);
    }

    @Test
    public void test04(){
        t_manager tManager = new t_manager();
        tManager.setTransactionId("11111111111111");

    }
    @Test
    public void test05() throws UnsupportedEncodingException, DecoderException {
        System.out.println(Hex.encodeHexString("1111111111111111111111111".getBytes("utf-8")));
        System.out.println(Hex.decodeHex("31313131313131313131313131313131313131313131313131"));
    }

}
