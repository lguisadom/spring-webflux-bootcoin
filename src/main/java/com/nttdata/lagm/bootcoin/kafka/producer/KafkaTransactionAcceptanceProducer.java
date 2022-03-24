package com.nttdata.lagm.bootcoin.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.nttdata.lagm.bootcoin.kafka.message.TransactionAcceptanceMessage;

@Component
public class KafkaTransactionAcceptanceProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTransactionAcceptanceProducer.class);

    private final KafkaTemplate<String, TransactionAcceptanceMessage> kafkaTemplate;

    public KafkaTransactionAcceptanceProducer(@Qualifier("kafkaTransactionAcceptanceTemplate") KafkaTemplate<String, TransactionAcceptanceMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(TransactionAcceptanceMessage transactionAcceptanceMessage) {
        LOGGER.info("Producing message {}", transactionAcceptanceMessage);
        this.kafkaTemplate.send("TOPIC-Transaction-Acceptance", transactionAcceptanceMessage);
    }

}