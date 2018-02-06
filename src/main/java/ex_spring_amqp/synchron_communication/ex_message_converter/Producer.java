package ex_spring_amqp.synchron_communication.ex_message_converter;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Producer {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(HelloWorldConfiguration_with_convertor.class);
        AmqpTemplate template = applicationContext.getBean(RabbitTemplate.class);

        Person p1 = new Person("John", "Doe", 25);

        template.convertAndSend(p1);


    }
}
