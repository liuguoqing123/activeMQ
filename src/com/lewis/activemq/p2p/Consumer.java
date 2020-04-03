package com.lewis.activemq.p2p;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
public class Consumer  {
    public static void main(String[] args) throws JMSException {
        System.out.println("+++++++++++++");
        ConnectionFactory connectionFactory;//消息中间件的连接工厂
        Connection connection = null;//连接
        Session session =null; // 会话
        Destination destination;//消息目的地
        MessageConsumer messageConsumer =null; //消息消费者
        //实例化连接工厂，创建一个连接
        connectionFactory = new ActiveMQConnectionFactory(
                JmsProducer.USERNAME,JmsProducer.PASSWORD,JmsProducer.BROKENURL
        );
        //通过连接工厂获取连接
        try {
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session,进行消息的发送
            session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            //创建消息队列
            destination = session.createQueue("hello_lewis");
            //创建消息消费者
            messageConsumer = session.createConsumer(destination);
            //模拟消息发送
            while (true){
                TextMessage textMessage = (TextMessage) messageConsumer.receive(1000);
                if(null!=textMessage){
                    System.out.println("收到了消息"+textMessage);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
    }
}

