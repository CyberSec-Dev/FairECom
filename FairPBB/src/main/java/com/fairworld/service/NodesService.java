package com.fairworld.service;

import com.fairworld.pojo.Nodes;
import java.util.List;

public interface NodesService {

    List<Nodes> getNodesById(String id);

    int insertNodes(Nodes nodes);

    Nodes getNodeByRoot(String priceRoot,String productId);
}
