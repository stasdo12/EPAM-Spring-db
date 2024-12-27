package com.epam.springcore.task.producer;


import com.epam.springcore.task.dto.TrainingRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class RabbitMQSender {
    private final Logger log = LoggerFactory.getLogger(RabbitMQSender.class);
    @Value("${exchange.rabbitMQ.name}")
    private String exchangeName;
    @Value("${routingKey.rabbitMQ.name}")
    private String routingKey;
    private final AmqpTemplate amqpTemplate;


    public void sendTrainingRequest(TrainingRequest trainingRequest, String transactionId, String jwtToken){
        amqpTemplate.convertAndSend(
                exchangeName,
                routingKey,
                trainingRequest,
                message -> {
                    message.getMessageProperties().setHeader("Transaction-ID", transactionId);
                    message.getMessageProperties().setHeader("Authorization", jwtToken);
                    return message;
                }
        );
        log.debug("Sending message to RabbitMQ. Transaction-ID: {}", trainingRequest);
    }
}
