package ex_spring_amqp.synchron_communication.demo;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Consummer {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        AmqpTemplate template = applicationContext.getBean(RabbitTemplate.class);

        //This template uses the default SimpleMessageConverter
        //If we want we could configure the template to use another converter
        System.out.println("Received: " + template.receiveAndConvert());
    }
}
