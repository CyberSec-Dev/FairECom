package dao;


import org.apache.ibatis.annotations.Param;
import pojo.t_manager;

import java.util.List;

public interface ManagerMapper {

    List<t_manager> getManager(@Param("serviceId")String serviceId, @Param("price")double price);

    int insertManager(t_manager manager);
}
