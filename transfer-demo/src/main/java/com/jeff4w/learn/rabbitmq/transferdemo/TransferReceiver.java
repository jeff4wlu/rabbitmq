package com.jeff4w.learn.rabbitmq.transferdemo;

import com.jeff4w.learn.rabbitmq.transferdemo.domain.Transaction;
import com.rabbitmq.client.AMQP;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

    /**
     * 消费者接收消息并消费消息
     *
     * @param order
     * @param headers
     * @param channel
     * @throws Exception
     */
    @RabbitHandler
    public void onTransferMessage(@Payload Transaction transaction) throws Exception {
        System.out.println("--------------收到消息，开始消费------------");
        System.out.println("订单ID是：" );

    }


}