package com.fairworld.service;

import com.fairworld.pojo.node;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NodeService {
    int addNode(node nodes);

    List<node> IfExistProduct(@Param("productId") String productId);
}
