package com.URL.ScaleLink.Service;


import com.URL.ScaleLink.Config.RabbitMq;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {
    private final RabbitTemplate rabbitTemplate;

    public void sendClickEvent(String shortCode){
        rabbitTemplate.convertAndSend(
                RabbitMq.CLICK_QUEUE,
                shortCode
        );
    }
}
