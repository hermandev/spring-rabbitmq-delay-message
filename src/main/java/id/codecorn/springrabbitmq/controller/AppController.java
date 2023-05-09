package id.codecorn.springrabbitmq.controller;

import id.codecorn.springrabbitmq.config.MQConfig;
import id.codecorn.springrabbitmq.dto.AppMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppController {
    private final RabbitTemplate template;


    @GetMapping("/app")
    public String getApp() {
        return "Hello World";
    }

    @PostMapping("/push")
    public String pushMessage(@RequestBody AppMessage req) {
        req.setMessageId(UUID.randomUUID().toString());
        req.setMessageDate(new Date());
        req.setMessage("PESAN 1");
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, req, message -> {
            message.getMessageProperties().setHeader("x-delay", 5000);
            return message;
        });

        req.setMessage("PESAN 2");
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, req, message -> {
            message.getMessageProperties().setHeader("x-delay", 10000);
            return message;
        });

        req.setMessage("PESAN 3");
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, req, message -> {
            message.getMessageProperties().setHeader("x-delay", 15000);
            return message;
        });
        return "Message Pushing..!";
    }
}
