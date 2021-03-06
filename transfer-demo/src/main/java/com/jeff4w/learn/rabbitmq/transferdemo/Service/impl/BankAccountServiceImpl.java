package com.jeff4w.learn.rabbitmq.transferdemo.Service.impl;

import com.jeff4w.learn.rabbitmq.transferdemo.Service.BankAccountService;
import com.jeff4w.learn.rabbitmq.transferdemo.Service.TransactionService;
import com.jeff4w.learn.rabbitmq.transferdemo.dao.BankAccountDao;
import com.jeff4w.learn.rabbitmq.transferdemo.domain.BankAccount;
import com.jeff4w.learn.rabbitmq.transferdemo.domain.Transaction;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
        if (balance.compareTo(BigDecimal.ZERO) >= 0) {
            bankAccount.setMoney(balance);
            addBankAccount(bankAccount);

            //这个id保证发送消息失败后可以重发具体的那条消息
            String msgId = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(msgId);
            Transaction transaction = new Transaction();
            transaction.setCreateDate(new Date());
            transaction.setFromAccountId(fromId);
            transaction.setToAccountId(toId);
            transaction.setMoneyTransfer(money);
            transaction.setRemark("已扣款但未加款");
            transaction.setMsgId(msgId);
            transactionService.addTransaction(transaction);

            rabbitTemplate.convertAndSend("transferdemo", "transfer", transaction, correlationId);
            System.out.println("异步发送消息成功！" +  msgId);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public Boolean receiveTransfer(Transaction transaction) throws Exception {
        BankAccount bankAccount = findBankAccountById(transaction.getToAccountId()).get();
        BigDecimal balance = bankAccount.getMoney().add(transaction.getMoneyTransfer());
        bankAccount.setMoney(balance);
        addBankAccount(bankAccount);

        transaction.setRemark("分布式转账完成");
        transactionService.addTransaction(transaction);

        return true;
    }

}
