package ex_spring_amqp.asynchron_communication;

public class HelloWorldMessageHandler {

    public void handleMessage(String message) {
        System.out.println("Received: " + message);
    }
}
