package dao;

import org.apache.ibatis.annotations.Param;
import pojo.node;

import java.util.List;

public interface NodeMapper {
    int addNode(node nodes);

    List<node> IfExistProduct(@Param("productId") String productId);

    List<node> getPriceRoot(@Param("productId") String productId,@Param("price") double price);
}
