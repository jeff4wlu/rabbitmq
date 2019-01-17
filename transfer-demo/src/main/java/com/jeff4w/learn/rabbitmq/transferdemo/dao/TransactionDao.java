package com.jeff4w.learn.rabbitmq.transferdemo.dao;

import com.jeff4w.learn.rabbitmq.transferdemo.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-16 13:40
 */
public interface TransactionDao extends JpaRepository<Transaction, Long> {
}