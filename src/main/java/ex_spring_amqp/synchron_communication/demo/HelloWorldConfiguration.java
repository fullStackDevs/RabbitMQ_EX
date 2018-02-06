package ex_spring_amqp.synchron_communication.demo;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfiguration {

    public final static String QUEUE_NAME = "hello.world.queue_v2";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }


    //###***###
    //The configuration also contains an instance of RabbitAdmin, which by default looks
    //for any beans of type Exchange, Queue, or Binding and then declares them on the broker.
    //###***###
    //If we don't add this bean the queue declared below ("hello.world.queue_v2") with the method "queueHellowWorld"
    //will NOT be added in RabbitMQ and when we try to read from that queue it will give us ann error
    @Bean
    AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());

        //set for writing to the queue using this rabbit template
        rabbitTemplate.setRoutingKey(QUEUE_NAME);

        //set for reading from queue using this rabbit template
        rabbitTemplate.setQueue(QUEUE_NAME);

        return rabbitTemplate;
    }

    @Bean
    public Queue queueHellowWorld() {
        return new Queue(QUEUE_NAME);
    }


}
