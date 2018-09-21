package com.luqi.rab.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LUCCI
 * @date 2018/9/21 11:32
 * @Description: direct模式生产者
 * @Modify: 所有生产者提交的消息都由Exchange来接受(指定routingKey)，然后Exchange按照特定的策略转发到Queue进行存储
 *          任何发送到Direct Exchange的消息都会被转发到RouteKey中指定的Queue。
 *
 *          注意:
 *          1.一般情况可以使用rabbitMQ自带的Exchange：””(该Exchange的名字为空字符串，下文称其为default Exchange)。

            2.这种模式下不需要将Exchange进行任何绑定(binding)操作

            3.消息传递时需要一个“RouteKey”，可以简单的理解为要发送到的队列名字。

            4.如果vhost中不存在RouteKey中指定的队列名，则该消息会被抛弃。

本文来自 道秋adol 的CSDN 博客 ，全文地址请点击：https://blog.csdn.net/chendaoqiu/article/details/48440633?utm_source=copy

---------------------
 */
@Slf4j
public class DirectProducer {

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
            String exchangeName = "test_direct_exchange";
            String routingKey = "test.direct";

            String mes = "2018-09-21 发direct模式 发消息！！";
            channel.basicPublish(exchangeName,routingKey,null,mes.getBytes());
            log.info("消息发送成功！");
        }catch (Exception e){
            log.info("direct模式消费者异常",e);
        }
    }
}
