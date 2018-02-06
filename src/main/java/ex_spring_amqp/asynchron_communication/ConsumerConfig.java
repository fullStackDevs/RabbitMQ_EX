package ex_spring_amqp.asynchron_communication;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;


public class ConsumerConfig {

    public static final String QUEUE_NAME = "hello.world.queue.asynchronous";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    //##**##
    //The messageListenerContainer creates a bridge between the queue and the method that handles the messages from the queue
    //The SimpleMessageListenerContainer is a Spring lifecycle component and will start automatically by default.
    //If you look in the Consumer class, you will see that its main() method consists of nothing more than a one-line bootstrap to create the ApplicationContext.
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(new MessageListenerAdapter(new HelloWorldMessageHandler()));
        return container;
    }

    //##**##
    //If the queue that we are using has not been created before by the message broker we need to declare a bean for that queue
    //*** And in order for the queue to be created we need to create a bean RabbitAdmin
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    //##**##
    //We need this bean so that the queues that we are declaring in this configuration to be created be the message broker
    //If we don't declare this bean the queues will not be created regardless of the fact that we have declared beans for queues
    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }


}
