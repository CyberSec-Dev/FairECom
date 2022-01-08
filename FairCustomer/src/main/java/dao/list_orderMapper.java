package dao;

import org.apache.ibatis.annotations.Param;
import pojo.Nodes;
import pojo.list_order;

import java.util.List;

public interface list_orderMapper {
   List<list_order> getList_order(@Param("productId") String productId);
   List<list_order> getList(@Param("productId") String productId,@Param("price") double price);
}
