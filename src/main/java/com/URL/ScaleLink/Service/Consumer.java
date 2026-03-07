package com.URL.ScaleLink.Service;


import com.URL.ScaleLink.Config.RabbitMq;
import com.URL.ScaleLink.Repository.UrlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Consumer {
    private final UrlRepo urlRepo;
    @RabbitListener(queues = RabbitMq.CLICK_QUEUE)
    public void consumeClickEvent(String shortCode){
        urlRepo.incrementClickCount(shortCode);
    }
}
