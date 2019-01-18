package com.jeff4w.learn.rabbitmq.transferdemo.Service.impl;

import com.jeff4w.learn.rabbitmq.transferdemo.Service.TransactionService;
import com.jeff4w.learn.rabbitmq.transferdemo.dao.TransactionDao;
import com.jeff4w.learn.rabbitmq.transferdemo.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-16 13:43
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionDao transactionDao;

    @Override
    @Transactional
    public Transaction addTransaction(Transaction transaction) throws Exception {
        // TODO Auto-generated method stub
        return transactionDao.save(transaction);
    }

    @Override
    public void delTransactionById(Long id) {
        // TODO Auto-generated method stub
        transactionDao.deleteById(id);

    }

    @Override
    public void updateTransaction(Transaction transaction) {
        // TODO Auto-generated method stub
        transactionDao.save(transaction);
    }

    @Override
    public Optional<Transaction> findTransactionById(Long id) {
        // TODO Auto-generated method stub
        return transactionDao.findById(id);
    }

    @Override
    public List<Transaction> findAllTransaction() {
        // TODO Auto-generated method stub
        return transactionDao.findAll();
    }

    @Override
    public List<Transaction> findTransactionByMsgId(String msgId) {
        // TODO Auto-generated method stub
        Transaction transaction = new Transaction();
        transaction.setMsgId(msgId);
        Example<Transaction> example = Example.of(transaction);
        return transactionDao.findAll(example);
    }

}
