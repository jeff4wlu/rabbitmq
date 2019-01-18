package com.jeff4w.learn.rabbitmq.transferdemo.component;

import com.jeff4w.learn.rabbitmq.transferdemo.Service.BankAccountService;
import com.jeff4w.learn.rabbitmq.transferdemo.domain.Transaction;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

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

    @RabbitHandler
    public void onTransferMessage(Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag, @Payload Transaction transaction) throws Exception {
        System.out.println("--------------收到消息，开始消费------------");
        System.out.println("订单ID是：" );
        bankAccountService.receiveTransfer(transaction);
        channel.basicAck(tag,false);            // 确认消息
    }

}
