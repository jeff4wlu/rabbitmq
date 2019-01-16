package com.jeff4w.learn.rabbitmq.transactiondemo.Service.impl;

import com.jeff4w.learn.rabbitmq.transactiondemo.Service.BankAccountService;
import com.jeff4w.learn.rabbitmq.transactiondemo.Service.TransactionService;
import com.jeff4w.learn.rabbitmq.transactiondemo.common.RestResultGenerator;
import com.jeff4w.learn.rabbitmq.transactiondemo.dao.BankAccountDao;
import com.jeff4w.learn.rabbitmq.transactiondemo.domain.BankAccount;
import com.jeff4w.learn.rabbitmq.transactiondemo.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-15 20:23
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountDao bankAccountDao;

    @Autowired
    private TransactionService transactionService;

    @Override
    public BankAccount addBankAccount(BankAccount bankAccount) {
        // TODO Auto-generated method stub
        return bankAccountDao.save(bankAccount);
    }

    @Override
    public void delBankAccountById(Long id) {
        // TODO Auto-generated method stub
        bankAccountDao.deleteById(id);

    }

    @Override
    public void updateBankAccount(BankAccount bankAccount) {
        // TODO Auto-generated method stub
        bankAccountDao.save(bankAccount);
    }

    @Override
    public Optional<BankAccount> findBankAccountById(Long id) {
        // TODO Auto-generated method stub
        return bankAccountDao.findById(id);
    }

    @Override
    public List<BankAccount> findAllBankAccount() {
        // TODO Auto-generated method stub
        return bankAccountDao.findAll();
    }

    @Override
    @Transactional
    public Boolean transfer(Long fromId, Long toId, BigDecimal money) throws Exception {
        BankAccount bankAccount = findBankAccountById(fromId).get();
        BigDecimal balance = bankAccount.getMoney().subtract(money);
        if(balance.compareTo(BigDecimal.ZERO) >= 0){
            bankAccount.setMoney(balance);
            addBankAccount(bankAccount);

            Transaction transaction = new Transaction();
            transaction.setCreateDate(new Date());
            transaction.setFromAccountId(fromId);
            transaction.setToAccountId(toId);
            transaction.setMoneyTransfer(money);
            transaction.setRemark("已扣款但未加款");
            transactionService.addTransaction(transaction);

            return true;
        }

        return false;
    }

}