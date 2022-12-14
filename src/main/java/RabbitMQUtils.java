import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import javafx.util.converter.ShortStringConverter;

public class RabbitMQUtils {

    //服务信息
    public static String host = "yun3";
    public static Integer port = 5672;
    public static String userName = "admin";
    public static String passwd = "13938082759";
    public static String virtualHost = "/";


    //交换机信息
    public static String exchangeName = "test_exc"; //交换机
    public static String routingKey = "route_key_test_queue"; //路由key

    public static ConnectionFactory connectionFactory = null;
    //初始化工厂
    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(passwd);
        connectionFactory.setVirtualHost(virtualHost);
    }

    //获取连接
    public static Connection getConnection() throws Exception {
        return connectionFactory.newConnection();
    }


    //发送消息，此时是生产者
    public static void sendMsg(String json) throws Exception{
        //1 获取连接
        Connection connection = getConnection();;

        //2 创建通道
        Channel channel = connection.createChannel();

        //3 发送数据
        channel.basicPublish(exchangeName, routingKey, null, json.getBytes());
        System.out.println("发送成功: " + json);

        //4 关闭资源
        if(channel != null) channel.close();
        if(connection != null)connection.close();
    }

    public static void main(String[] args) throws Exception {
        //测试生产者
        sendMsg("test");
    }
}
