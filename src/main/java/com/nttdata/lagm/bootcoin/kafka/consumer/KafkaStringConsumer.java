package com.nttdata.lagm.bootcoin.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.nttdata.lagm.bootcoin.service.TransactionAcceptanceService;

@Component
public class KafkaStringConsumer {

	private Logger LOGGER = LoggerFactory.getLogger(KafkaStringConsumer.class);

	@Autowired
	private TransactionAcceptanceService transactionAcceptanceService;
	
	@KafkaListener(
			topics = "#{'${kafka.topic.topicTransactionResult}'}", 
			groupId = "bootcamp2022")
	public void consume(String message) {
		LOGGER.info("Consuming Message {}", message);
		String[] arr = message.split(":");
		String status = arr[0].replaceAll("\"", "");
		String transactionId = arr[1].replaceAll("\"", "");
		LOGGER.info("Updating status={}, transactionId={}", status, transactionId);
		transactionAcceptanceService.updateStatus(status, transactionId).subscribe();
	}

}