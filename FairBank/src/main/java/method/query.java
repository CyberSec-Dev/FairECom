package method;

import dao.BankMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.Transaction;

import java.util.ArrayList;
import java.util.List;

public class query {
    public static Transaction getTransaction(String transactionId){
        SqlSession session = MybatisUtils.getSession();
        BankMapper bankMapper = session.getMapper(BankMapper.class);
        Transaction transaction = bankMapper.getTransaction(transactionId);
        return transaction;
    }
    public static void insertTransaction(Transaction transaction){
        SqlSession session = MybatisUtils.getSession();
        BankMapper bankMapper = session.getMapper(BankMapper.class);
        int b = bankMapper.insertTransaction(transaction);
        session.commit();

    }

}
