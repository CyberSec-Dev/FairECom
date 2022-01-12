package com.fairworld.service;

import com.fairworld.mapper.ManagerMapper;
import com.fairworld.pojo.t_manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerImpl implements ManagerService{
    @Autowired
    ManagerMapper managerMapper;
    @Override
    public int insertManager(t_manager manager) {
        return managerMapper.insertManager(manager);
    }
}
