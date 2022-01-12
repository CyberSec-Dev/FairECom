package com.fairworld.mapper;

import com.fairworld.pojo.Nodes;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NodesMapper {

    List<Nodes> getNodesById(String id);

    int insertNodes(Nodes nodes);

    Nodes getNodeByRoot(String priceRoot,String productId);
}
