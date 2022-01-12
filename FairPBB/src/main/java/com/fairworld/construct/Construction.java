package com.fairworld.construct;

import com.fairworld.mapper.list_order_mapper;
import com.fairworld.pojo.list_order;
import com.fairworld.service.list_order_Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class Construction {
    @Autowired
    list_order_Impl listOrder;
}
