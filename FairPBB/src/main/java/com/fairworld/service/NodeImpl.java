package com.fairworld.service;

import com.fairworld.mapper.NodeMapper;
import com.fairworld.mapper.NodesMapper;
import com.fairworld.pojo.node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeImpl implements NodeService{

    @Autowired
    NodeMapper nodeMapper;

    @Override
    public int addNode(node nodes) {
        return nodeMapper.addNode(nodes);
    }

    @Override
    public List<node> IfExistProduct(String productId) {
        return nodeMapper.IfExistProduct(productId);
    }
}
