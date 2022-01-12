package com.fairworld.mapper;

import com.fairworld.pojo.list_order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface list_order_mapper {
    List<list_order> getOrders();

    List<list_order> getOrderById(String id);

    List<list_order> getOrderByPrice(String id,double price);
}
