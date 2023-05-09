package id.codecorn.springrabbitmq.listener;

import id.codecorn.springrabbitmq.config.MQConfig;
import id.codecorn.springrabbitmq.dto.AppMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageListener {

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listenMessage(AppMessage message) throws IOException {
        System.out.println(message);
    }
}
