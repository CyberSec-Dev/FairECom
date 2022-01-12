package com.fairworld.service;

import com.fairworld.pojo.list_order;

import java.util.List;

public interface list_order_service {
    List<list_order> getOrders();

    List<list_order> getOrderById(String id);

    List<list_order> getOrderByPrice(String id,double price);
}
