package ex_spring_amqp.synchron_communication.demo;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Producer {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        AmqpTemplate template = applicationContext.getBean(AmqpTemplate.class);

        //This template used the default SimpleMessageConverter because we didn't configured another one
        template.convertAndSend("Hello World *** !");
        System.out.println("Sent: Hello World *** !");
    }
}
