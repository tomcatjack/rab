package com.luqi.rab.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LUCCI
 * @date 2018/9/21 14:31
 * @Description: topic模式生产者
 * @Modify:  1.这种模式较为复杂，简单来说，就是每个队列都有其关心的主题，所有的消息都带有一个“标题”(RouteKey)，Exchange会将消息转发到所有关注主题能与RouteKey模糊匹配的队列。

---------------------

本文来自 道秋adol 的CSDN 博客 ，全文地址请点击：https://blog.csdn.net/chendaoqiu/article/details/48440633?utm_source=copy
 */
@Slf4j
public class TopicProducer {

    public static void main(String[] args) {
        try {
            //1.创建 connectionFactory
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            connectionFactory.setPort(5672);
            connectionFactory.setVirtualHost("/");

            //2.创建connection
            Connection connection = connectionFactory.newConnection();

            //3.创建Channel
            Channel channel = connection.createChannel();

            //4 声明
            String exchangeName = "test_topic_exchange";
            String routingKey1 = "user.save";
            String routingKey2 = "user.update";
            String routingKey3 = "user.delete.abc";

            String mes = "2018-09-21 发topic模式 发消息！！";
            channel.basicPublish(exchangeName,routingKey1,null,mes.getBytes());
            channel.basicPublish(exchangeName,routingKey2,null,mes.getBytes());
            channel.basicPublish(exchangeName,routingKey3,null,mes.getBytes());
        }catch (Exception e){
            log.info("topic模式消费者异常",e);
        }
    }

}
