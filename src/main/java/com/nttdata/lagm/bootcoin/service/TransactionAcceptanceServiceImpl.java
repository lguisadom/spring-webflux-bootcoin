package com.nttdata.lagm.bootcoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.bootcoin.controller.dto.request.TransactionAcceptanceRqDto;
import com.nttdata.lagm.bootcoin.kafka.message.TransactionAcceptanceMessage;
import com.nttdata.lagm.bootcoin.kafka.producer.KafkaTransactionAcceptanceProducer;
import com.nttdata.lagm.bootcoin.model.TransactionAcceptance;
import com.nttdata.lagm.bootcoin.repository.TransactionAcceptanceRepository;
import com.nttdata.lagm.bootcoin.repository.TransactionRequestRepository;
import com.nttdata.lagm.bootcoin.service.util.Constants;
import com.nttdata.lagm.bootcoin.service.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionAcceptanceServiceImpl implements TransactionAcceptanceService {
	
	@Autowired
	private TransactionAcceptanceRepository transactionAcceptanceRepository;
	
	@Autowired
	private TransactionRequestRepository transactionRequestRepository;
	
	private final KafkaTransactionAcceptanceProducer kafkaTransactionAcceptanceProducer;

    @Autowired
    TransactionAcceptanceServiceImpl(KafkaTransactionAcceptanceProducer kafkaTransactionAcceptanceProducer) {
        this.kafkaTransactionAcceptanceProducer = kafkaTransactionAcceptanceProducer;
    }
    
	@Override
	public Mono<TransactionAcceptance> create(TransactionAcceptanceRqDto transactionAcceptanceRqDto) {
		// TODO Validate seller: Identification, available amount (bankAccount o Wallet) 
		return transactionRequestRepository.findById(transactionAcceptanceRqDto.getIdTransactionRequest())
			.flatMap(transactionRequest -> {
				transactionRequest.setStatus(Constants.STATUS_PROCESSING);
				transactionRequestRepository.save(transactionRequest).subscribe();
				TransactionAcceptance transactionAcceptance = new TransactionAcceptance();
				transactionAcceptance.setSellerIdentification(transactionAcceptanceRqDto.getSellerIdentification());
				transactionAcceptance.setDate(Util.getToday());
				transactionAcceptance.setStatus(Constants.STATUS_PROCESSING);
				transactionAcceptance.setTransactionRequest(transactionRequest);
				transactionRequest.setStatus(Constants.STATUS_PROCESSING);
				
//				TransactionAcceptanceMessage transactionAcceptanceMessage = new TransactionAcceptanceMessage();
//				transactionAcceptanceMessage.setId("1");
//				transactionAcceptanceMessage.setSellerIdentification(transactionRequest.getIdentification());
				kafkaTransactionAcceptanceProducer.sendMessage(transactionAcceptance);
				
				// After validations to send to MS to register the transaction
				return transactionAcceptanceRepository.save(transactionAcceptance);
			});
	}

	@Override
	public Flux<TransactionAcceptance> findAll() {
		return transactionAcceptanceRepository.findAll();
	}

	@Override
	public Flux<TransactionAcceptance> getAllProcessingAcceptance() {
		return transactionAcceptanceRepository.findAll().filter(t -> Constants.STATUS_PROCESSING.equalsIgnoreCase(t.getStatus()));
	}
}
