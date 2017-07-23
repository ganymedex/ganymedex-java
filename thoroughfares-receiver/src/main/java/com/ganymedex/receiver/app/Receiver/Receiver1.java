package com.ganymedex.receiver.app.Receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/*
@Component
@RabbitListener(queues = "hello")
public class Receiver1 {

    @RabbitHandler
    public void processMessage1(String msg) {
        //System.out.println(Thread.currentThread().getName() + " 2接收到来自helloQueue队列的消息：" + msg);
        System.out.println(Thread.currentThread().getName() +"2222"+   msg);
    }
}
*/
