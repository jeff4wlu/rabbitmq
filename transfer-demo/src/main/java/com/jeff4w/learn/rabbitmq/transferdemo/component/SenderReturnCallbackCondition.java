package com.jeff4w.learn.rabbitmq.transferdemo.component;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-18 15:09
 */
public class SenderReturnCallbackCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        if(conditionContext.getEnvironment().getProperty("spring.rabbitmq.publisher-returns") == "true" &&
                conditionContext.getEnvironment().getProperty("project.sender") == "true"){
            return true;
        }

        return false;
    }
}
