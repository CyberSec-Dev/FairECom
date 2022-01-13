package utils;

import dao.ManagerMapper;
import dao.NodeMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.node;

import java.util.ArrayList;
import java.util.List;

public class Nodes {
    public static void insertNode(node node){
        SqlSession session = MybatisUtils.getSession();
        NodeMapper mapper = session.getMapper(NodeMapper.class);
        mapper.addNode(node);
        ManagerMapper managerMapper = session.getMapper(ManagerMapper.class);
        session.commit();
        session.close();
    }

    public static boolean IfExist(String productId){
        SqlSession session = MybatisUtils.getSession();
        NodeMapper mapper = session.getMapper(NodeMapper.class);
        List<node> nodes = new ArrayList<node>();
        nodes = mapper.IfExistProduct(productId);
        session.close();
        return (nodes.size() == 0);
    }

    public static String getContractAddress(String productId){
        SqlSession session = MybatisUtils.getSession();
        NodeMapper mapper = session.getMapper(NodeMapper.class);
        List<node> nodes = new ArrayList<node>();
        nodes = mapper.IfExistProduct(productId);
        session.close();
        return nodes.get(0).getContractHash();
    }

    public static int getIndex(String productId){
        SqlSession session = MybatisUtils.getSession();
        NodeMapper mapper = session.getMapper(NodeMapper.class);
        List<node> nodes = new ArrayList<node>();
        nodes = mapper.IfExistProduct(productId);
        session.close();
        return nodes.size();
    }
}
