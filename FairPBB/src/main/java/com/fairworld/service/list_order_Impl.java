package com.fairworld.service;

import com.fairworld.mapper.list_order_mapper;
import com.fairworld.pojo.list_order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class list_order_Impl implements list_order_service {

    @Autowired
    private list_order_mapper listOrderMapper;

    @Override
    public List<list_order> getOrders() {
        return listOrderMapper.getOrders();
    }

    @Override
    public List<list_order> getOrderById(String id) {
        return listOrderMapper.getOrderById(id);
    }

    @Override
    public List<list_order> getOrderByPrice(String id, double price) {
        return listOrderMapper.getOrderByPrice(id,price);
    }
}
