package com.ganymedex.receiver.app.Receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/*@Component
@RabbitListener(queues = "hello")
public class Receiver {

    @RabbitHandler
    public void processMessage1(String msg) {
       // System.out.println(Thread.currentThread().getName() + " 接收到来自helloQueue队列的消息：" + msg);
        System.out.println(Thread.currentThread().getName() +"1111"+  msg);
    }

 *//*   @RabbitHandler
    public void processMessage2(String msg) {
        System.out.println(Thread.currentThread().getName() + " 接收到来自helloQueue队列的消息：" + msg);
    }*//*
}*/
