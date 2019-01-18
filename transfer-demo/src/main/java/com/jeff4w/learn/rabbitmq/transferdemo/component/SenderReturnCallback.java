package com.jeff4w.learn.rabbitmq.transferdemo.component;

import com.jeff4w.learn.rabbitmq.transferdemo.Service.TransactionService;
import com.jeff4w.learn.rabbitmq.transferdemo.domain.Transaction;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-17 15:08
 */
@Conditional(SenderReturnCallbackCondition.class)
@Component
public class SenderReturnCallback implements RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TransactionService transactionService;

    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnCallback(this);             //指定 ReturnCallback
        rabbitTemplate.setMandatory(true);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息主体 message : " + message);
        System.out.println("消息主体 message : " + replyCode);
        System.out.println("描述：" + replyText);
        System.out.println("消息使用的交换器 exchange : " + exchange);
        System.out.println("消息使用的路由键 routing : " + routingKey);

        Jackson2JsonMessageConverter jackson2JsonMessageConverter =new Jackson2JsonMessageConverter();
        Transaction transaction = (Transaction) jackson2JsonMessageConverter.fromMessage(message);
        String msgId = transaction.getMsgId();
        List<Transaction> transactions = transactionService.findTransactionByMsgId(msgId);
        if (transactions.size() > 0) {
            CorrelationData correlationData = new CorrelationData(msgId);
            rabbitTemplate.convertAndSend("transferdemo", "transfer", transactions.get(0), correlationData);
        }
    }

}

