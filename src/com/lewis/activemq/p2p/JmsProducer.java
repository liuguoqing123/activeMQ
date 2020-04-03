package com.lewis.activemq.p2p;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
public class JmsProducer  {
    public static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    public static final String BROKENURL = "failover://tcp://192.168.1.10:61616";//ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory;//消息中间件的连接工厂
        Connection connection = null;//连接
        Session session =null; // 会话
        Destination destination;//消息目的地
        MessageProducer messageProducer =null; //消息生产者
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
            //创建消息生产者
            messageProducer = session.createProducer(destination);
            //模拟消息发送
            for (int i = 0; i < 5; i++) {
                TextMessage textMessage = session.createTextMessage("给妈妈发送消息");
                System.out.println("textMessage:"+textMessage);
                messageProducer.send(textMessage);
            }
            //如果设置了事务，session就必须的提交
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                    connection.close();
            }
        }
    }
}
