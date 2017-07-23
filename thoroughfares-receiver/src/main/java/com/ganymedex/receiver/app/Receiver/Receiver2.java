package com.ganymedex.receiver.app.Receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/*
@Component
@RabbitListener(queues = "hello")
public class Receiver2 {


    @RabbitHandler
    public void processUser(String message) {
        //System.out.printf(Thread.currentThread().getName()+"\"用户侧流水:{}\",message"+message);
        System.out.println(Thread.currentThread().getName() +"用户侧流水:["+ message+"]");
    }


}*/
