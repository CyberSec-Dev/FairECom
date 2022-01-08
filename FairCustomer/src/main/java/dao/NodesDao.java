package dao;

import org.apache.ibatis.annotations.Param;
import pojo.Nodes;

import java.util.List;

public interface NodesDao {
    Nodes getNodes(@Param("productId")String productId, @Param("price")double price);
}
