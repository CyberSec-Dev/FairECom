package utils;

import dao.ManagerMapper;
import jdk.internal.org.objectweb.asm.util.TraceAnnotationVisitor;
import msg.TransactionSign;
import org.apache.ibatis.session.SqlSession;
import pojo.t_manager;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class query {


    public static void insertTransactions(ArrayList<t_manager> t_manager){
        SqlSession session = MybatisUtils.getSession();
        ManagerMapper managerMapper = session.getMapper(ManagerMapper.class);
        for(int i=0;i<t_manager.size();i++) {
            int b = managerMapper.insertt_manager(t_manager.get(i));
        }
        session.commit();

    }
    public static ArrayList<TransactionSign> getTransactions(String productId, double price) {
        SqlSession session = MybatisUtils.getSession();
        ManagerMapper managerMapper = session.getMapper(ManagerMapper.class);
        List<t_manager> manager = managerMapper.getTransactions(productId, price);
        ArrayList<TransactionSign> transactions = new ArrayList<TransactionSign>();
        for (t_manager t_manager : manager) {
            transactions.add(new TransactionSign(t_manager.getTransactionId(),t_manager.getServiceId(),t_manager.getVendorId(),t_manager.getBankId(),t_manager.getPrice(),t_manager.getPosition(),t_manager.getSignV(),t_manager.getSignB()));
        }

        return transactions;
    }
}
