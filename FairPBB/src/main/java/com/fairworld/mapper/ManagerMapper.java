package com.fairworld.mapper;

import com.fairworld.pojo.t_manager;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ManagerMapper {

    int insertManager(t_manager manager);
}
