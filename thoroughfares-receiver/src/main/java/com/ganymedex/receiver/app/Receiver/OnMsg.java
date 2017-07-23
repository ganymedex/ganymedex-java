package com.ganymedex.receiver.app.Receiver;

import com.rabbitmq.client.Channel;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class OnMsg implements ChannelAwareMessageListener {

    Logger log = Logger.getLogger("tesglog");



    private static final int THREADNUM = 3;
 /*   @PostConstruct
    public void init(){
        this.threadPool = Executors.newFixedThreadPool(THREADNUM);
        System.out.printf("ExcutorTestService.init finished……");
    }

    @PreDestroy
    public void destroy(){
        threadPool.shutdown();
        System.out.printf("ExcutorTestService.destroy finished……");
    }*/

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String boby = new String(message.getBody(), "utf-8");//转换消息，我们是使用json数据格式
        //ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Runnable() {   //多线程处理
            @Override
            public void run() {
                //Jedis jedis = jedisShardInfo.createResource();
                //jedis.sadd(TopicRabbitConfig.TRANSACTION_QUEUE, boby);//添加到key为当前消息类型的集合里面，防止丢失消息
                //if (boluomeFlowService.insert(flow)) {  //当添加成功时候返回成功
                    //System.out.printf("客户交易流水添加1条记录:{}", json);
                    //jedis.srem(TopicRabbitConfig.TRANSACTION_QUEUE, boby);//从当前消息类型集合中移除已经消费过的消息
                    try {
                        String boby = new String(message.getBody(), "utf-8");//转换消息，我们是使用json数据格式
                        //System.out.printf(Thread.currentThread().getName() +"TTTTTTTT客户交易流水添加:{}"+boby);
                        int max=20;
                        int min=10;
                        Random random = new Random();
                        int s = random.nextInt(max)%(max-min+1) + min;
                        Thread.currentThread().setName("线程"+s);
                        log.info(Thread.currentThread().getName() +"TTTTTTTT客户交易流水添加:{}"+boby);
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//手工返回ACK，通知此消息已经争取消费
                        // channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);//取消手工返回
                    } catch (IOException ie) {
                        System.out.printf("消费成功回调成功，io操作异常");
                    }
                //} else {
                    //System.out.printf("客户交易流水添加失败记录:{}", json);
                //}
            }
        });
    }
}

