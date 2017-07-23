package com.ganymedex.receiver.app.config;

import com.ganymedex.receiver.app.Receiver.OnMsg;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configssss {

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

    /**
     * 接受消息的监听，这个监听客户交易流水的消息
     * 针对消费者配置
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer1(ConnectionFactory connectionFactory, OnMsg transactionConsume) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(helloQueue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(transactionConsume);
        return container;
    }

}
