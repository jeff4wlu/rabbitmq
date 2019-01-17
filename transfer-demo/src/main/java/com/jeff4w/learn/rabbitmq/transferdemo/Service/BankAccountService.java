package com.jeff4w.learn.rabbitmq.transferdemo.Service;

import com.jeff4w.learn.rabbitmq.transferdemo.domain.BankAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-15 20:21
 */
public interface BankAccountService {
    public BankAccount addBankAccount(BankAccount bankAccount);
    public void delBankAccountById(Long id);
    public void updateBankAccount(BankAccount bankAccount);
    public Optional<BankAccount> findBankAccountById(Long id);
    public List<BankAccount> findAllBankAccount();
    public Boolean transfer(Long fromId, Long toId, BigDecimal money) throws Exception;
}
