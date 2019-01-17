package com.jeff4w.learn.rabbitmq.transferdemo;

import com.jeff4w.learn.rabbitmq.transferdemo.Service.BankAccountService;
import com.jeff4w.learn.rabbitmq.transferdemo.domain.Transaction;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-17 9:16
 */
@ConditionalOnProperty(name = "project.sender", havingValue = "false")
@Component
@RabbitListener(queues = "transfer")
public class TransferReceiver {

    @Autowired
    private BankAccountService bankAccountService;

    /**
     * 消费者接收消息并消费消息
     *
     * @param order
     * @param headers
     * @param channel
     * @throws Exception
     */
    @RabbitHandler
    public void onTransferMessage(Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Payload Transaction transaction) throws Exception {
        System.out.println("--------------收到消息，开始消费------------");
        System.out.println("订单ID是：" );
        bankAccountService.receiveTransfer(transaction);
        channel.basicAck(tag,false);            // 确认消息
    }

}
