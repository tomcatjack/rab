package com.luqi.rab.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LUCCI
 * @date 2018/9/21 11:36
 * @Description: direct模式消费者
 * @Modify:
 */
@Slf4j
public class DirectConsumer {

    public static void main(String[] args) {
        try {
            //1.创建 ConnectionFactory
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            connectionFactory.setPort(5672);
            connectionFactory.setVirtualHost("/");
            //网络有问题时，好后可自动恢复设置。
            connectionFactory.setAutomaticRecoveryEnabled(true);
            //重试一次 5s
            connectionFactory.setNetworkRecoveryInterval(5000);

            //2.创建一个 connection
            Connection connection = connectionFactory.newConnection();

            //3.创建一个 channel
            Channel channel = connection.createChannel();

            //4 声明
            String exchangeName = "test_direct_exchange";
            String exchangeType = "direct";
            String queueName = "test_direct_queue";
            String routingKey = "test.direct";

            //表示声明了一个交换机
            channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);

            //表示声明了一个队列 具体参数的意思跟进去看
            channel.queueDeclare(queueName, false, false, false, null);

            //建立绑定关系
            channel.queueBind(queueName, exchangeName, routingKey);

            //
            QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

            //参数：队列名称、是否自动ACK、Consumer
            channel.basicConsume(queueName, true, queueingConsumer);

            while (true) {
                QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
                String body = new String(delivery.getBody());
                log.info("direct模式消费者收到消息：{}", body);
            }
        } catch (Exception e) {

        }
    }
}
