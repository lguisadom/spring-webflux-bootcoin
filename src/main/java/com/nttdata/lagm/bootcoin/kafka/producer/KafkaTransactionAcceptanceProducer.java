package com.nttdata.lagm.bootcoin.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.nttdata.lagm.bootcoin.model.TransactionAcceptance;

@Component
public class KafkaTransactionAcceptanceProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTransactionAcceptanceProducer.class);

    private final KafkaTemplate<String, TransactionAcceptance> kafkaTemplate;

    public KafkaTransactionAcceptanceProducer(@Qualifier("kafkaTransactionAcceptanceTemplate") KafkaTemplate<String, TransactionAcceptance> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(TransactionAcceptance transactionAcceptance) {
        LOGGER.info("Producing message {}", transactionAcceptance);
        this.kafkaTemplate.send("TOPIC-Transaction-Acceptance", transactionAcceptance);
    }

}