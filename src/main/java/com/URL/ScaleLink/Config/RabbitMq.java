package com.URL.ScaleLink.Config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMq {

    public static final String CLICK_QUEUE="click-events";

    @Bean
    public Queue clickQueue(){
        return new Queue(CLICK_QUEUE,true);
    }
}
