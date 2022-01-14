package com.fairworld.utils;

import com.fairworld.contract.Storage;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ethStorage {

    private static String url = "https://rinkeby.infura.io/v3/34377eeeb81a4e87932af7b5b84296f1";
    private static Web3j web3j = Web3j.build(new HttpService(url));
    private static String password = "huangran123";
    private static String source = "./src/main/resources/wallet/UTC--2021-11-09T01-28-25.520Z--a014671b7a14c4da5b09d42668e56b2b9751f1d6";

    public static String deploy(){
        Credentials credentials  = null;
        Storage storage = null;
        try {
            credentials = WalletUtils.loadCredentials(password,source);
            storage = Storage.deploy(web3j,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT).send();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String address = storage.getContractAddress();
        return address;
    }

    public static void init(String contractAddress,String productId){

        Credentials credentials  = null;
        try {
            credentials = WalletUtils.loadCredentials(password,source);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        Storage product = Storage.load(contractAddress,web3j,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);
        try {
            product.init(productId).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getId(String contractAddress){
        Credentials credentials  = null;
        try {
            credentials = WalletUtils.loadCredentials(password,source);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        Storage product = Storage.load(contractAddress,web3j,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);
        String productId = null;
        try {
            productId = product.getId().send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productId;
    }

    public static String addPriceRoot(String contractAddress,String PriceRoot){
        Credentials credentials  = null;
        try {
            credentials = WalletUtils.loadCredentials(password,source);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        Storage product = Storage.load(contractAddress,web3j,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);
        String txHash = null;
        try {
            txHash = product.register(PriceRoot).send().getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txHash;
    }

    public static String getPrice(String contractAddress,int i){
        Credentials credentials  = null;
        try {
            credentials = WalletUtils.loadCredentials(password,source);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        Storage product = Storage.load(contractAddress,web3j,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);
        String priceroot= null;
        try {
             priceroot= product.getPriceRoot(BigInteger.valueOf(i)).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return priceroot;
    }

    public static int getListNumber(String contractAddress){
        Credentials credentials  = null;
        try {
            credentials = WalletUtils.loadCredentials(password,source);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        Storage product = Storage.load(contractAddress,web3j,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);
        BigInteger send = null;
        try {
            send = product.getNumber().send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return send.intValue();
    }
}
