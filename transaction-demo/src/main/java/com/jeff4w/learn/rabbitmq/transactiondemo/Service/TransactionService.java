package com.jeff4w.learn.rabbitmq.transactiondemo.Service;

import com.jeff4w.learn.rabbitmq.transactiondemo.domain.Transaction;

import java.util.List;
import java.util.Optional;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-16 13:42
 */
public interface TransactionService {
    public Transaction addTransaction(Transaction transaction) throws Exception;
    public void delTransactionById(Long id);
    public void updateTransaction(Transaction transaction);
    public Optional<Transaction> findTransactionById(Long id);
    public List<Transaction> findAllTransaction();
}
