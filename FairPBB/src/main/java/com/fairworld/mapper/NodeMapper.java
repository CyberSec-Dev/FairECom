package com.fairworld.mapper;

import com.fairworld.pojo.node;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NodeMapper {

    int addNode(node nodes);

    List<node> IfExistProduct(@Param("productId") String productId);


}

