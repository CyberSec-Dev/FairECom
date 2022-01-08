package dao;


import org.apache.ibatis.annotations.Param;
import pojo.Transaction;
import java.util.List;

public interface BankMapper {

    Transaction getTransaction(@Param("transactionId") String transactionId);
    int insertTransaction(Transaction transaction);

}
