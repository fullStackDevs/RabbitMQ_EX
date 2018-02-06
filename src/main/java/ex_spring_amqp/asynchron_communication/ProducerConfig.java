package ex_spring_amqp.asynchron_communication;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConfig {

    public static final String QUEUE_NAME = "hello.world.queue.asynchronous";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setRoutingKey(QUEUE_NAME);
        return rabbitTemplate;
    }


    @Bean
    public ScheduledProducer scheduledProducer() {
        return new ScheduledProducer();
    }


    //##**##
    //the "postProcessor" bean in the ProducerConfiguration is registering the task with a scheduler.
    @Bean
    public BeanPostProcessor postProcessor() {
        return new ScheduledAnnotationBeanPostProcessor();
    }

    //Without this the scheduler didn't work
    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(); //single threaded by default
    }


    static class ScheduledProducer{

        @Autowired
        RabbitTemplate template;

        public final AtomicInteger counter = new AtomicInteger();

        //##**##
        //This method will start executing automatically because it's annotated with @Scheduled and we created a bean BeanPostProcessor of type ScheduledAnnotationBeanPostProcessor
        @Scheduled(fixedRate = 3000)
        public void sendMessage() {
            template.convertAndSend("Message " + counter.incrementAndGet());
        }
    }


}
