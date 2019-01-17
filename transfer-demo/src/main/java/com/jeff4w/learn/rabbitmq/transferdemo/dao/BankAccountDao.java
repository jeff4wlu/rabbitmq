package com.jeff4w.learn.rabbitmq.transferdemo.dao;

import com.jeff4w.learn.rabbitmq.transferdemo.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-15 20:16
 */
public interface BankAccountDao extends JpaRepository<BankAccount, Long> {
}
