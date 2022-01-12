package com.fairworld.controller;

import com.fairworld.pojo.list_order;
import com.fairworld.service.list_order_Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    list_order_Impl list_order;
    @RequestMapping("/test")
    @ResponseBody
    public List<list_order> getOrders(){
        return list_order.getOrders();
    }

    @GetMapping("/Id/{id}")
    @ResponseBody
    public List<list_order> getOrderById(@PathVariable("id") String id){
        System.out.println(id);
        return list_order.getOrderById(id);
    }

}
