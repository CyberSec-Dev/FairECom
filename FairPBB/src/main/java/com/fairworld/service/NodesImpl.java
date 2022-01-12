package com.fairworld.service;

import com.fairworld.mapper.NodesMapper;
import com.fairworld.pojo.Nodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.server.PathParam;
import java.util.List;

@Service
public class NodesImpl implements NodesService{

    @Autowired
    NodesMapper nodesMapper;
    @Override
    public List<Nodes> getNodesById(String id) {
        return nodesMapper.getNodesById(id);
    }

    @Override
    public int insertNodes(Nodes nodes) {
        return nodesMapper.insertNodes(nodes);
    }

    @Override
    public Nodes getNodeByRoot(String priceRoot, String productId) {
        return nodesMapper.getNodeByRoot(priceRoot,productId);
    }

}
