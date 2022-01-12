package com.fairworld;

import com.fairworld.pojo.list_order;
import com.fairworld.service.list_order_Impl;
import com.fairworld.utils.ethStorage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BoardApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("test");

    }
    @Test
    public void test01(){
        String id = "99a4788cb24856965c36a24e339b6058";
        String address = ethStorage.deploy();
        ethStorage.init(address,id);
        ethStorage.addPriceRoot(address,"111111111111111111111111111");
        ethStorage.addPriceRoot(address,"222222222222222222222222222");
        ethStorage.addPriceRoot(address,"333333333333333333333333333");
        ethStorage.addPriceRoot(address,"444444444444444444444444444");
        ethStorage.addPriceRoot(address,"555555555555555555555555555");
        String id1 = ethStorage.getId(address);
        System.out.println(id1);
    }

}
