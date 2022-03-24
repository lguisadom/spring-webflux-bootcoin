package com.nttdata.lagm.bootcoin.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.nttdata.lagm.bootcoin.model.TransactionAcceptance;

@Configuration
public class KafkaTransactionAcceptanceConfig {

	@Value("${kafka.uri}")
	private String KAFKA_URI;
	
    public ProducerFactory<String, TransactionAcceptance> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_URI);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name = "kafkaTransactionAcceptanceTemplate")
    public KafkaTemplate<String, TransactionAcceptance> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
