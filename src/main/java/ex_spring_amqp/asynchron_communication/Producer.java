package ex_spring_amqp.asynchron_communication;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Producer {

    //##**##
    //The Producer’s main() method is a one-line bootstrap, since the component whose method is annotated with @Scheduled will start executing automatically.
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProducerConfig.class);
    }
}
