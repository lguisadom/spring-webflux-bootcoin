package com.nttdata.lagm.bootcoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.bootcoin.dto.request.TransactionAcceptanceRqDto;
import com.nttdata.lagm.bootcoin.kafka.producer.KafkaTransactionAcceptanceProducer;
import com.nttdata.lagm.bootcoin.model.TransactionAcceptance;
import com.nttdata.lagm.bootcoin.model.TransactionRequest;
import com.nttdata.lagm.bootcoin.proxy.AccountProxy;
import com.nttdata.lagm.bootcoin.repository.ExchangeRateRepository;
import com.nttdata.lagm.bootcoin.repository.TransactionAcceptanceRepository;
import com.nttdata.lagm.bootcoin.repository.TransactionRequestRepository;
import com.nttdata.lagm.bootcoin.util.Constants;
import com.nttdata.lagm.bootcoin.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionAcceptanceServiceImpl implements TransactionAcceptanceService {
	
	@Autowired
	private TransactionAcceptanceRepository transactionAcceptanceRepository;
	
	@Autowired
	private TransactionRequestRepository transactionRequestRepository;
	
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	
	@Autowired
	private AccountProxy accountProxy;
	
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
				return exchangeRateRepository.findTopByOrderByDateDesc()
					.flatMap(exchangeRate -> {
					TransactionAcceptance transactionAcceptance = new TransactionAcceptance();
					transactionAcceptance.setSellerIdentification(transactionAcceptanceRqDto.getSellerIdentification());
					transactionAcceptance.setDate(Util.getToday());
					transactionAcceptance.setExchangeRate(exchangeRate);
					transactionAcceptance.setTransactionId(Util.generateTransactionId());
					transactionAcceptance.setTransactionRequest(transactionRequest);
					
					// Validate transactionAcceptance
					return checkConditions(transactionAcceptance)
							.then(save(transactionRequest, transactionAcceptance));
				});
			}).switchIfEmpty(Mono.error(new Exception(
				"La solicitud de compra con id " + transactionAcceptanceRqDto.getIdTransactionRequest() + " no existe")));
	}	

	@Override
	public Flux<TransactionAcceptance> findAll() {
		return transactionAcceptanceRepository.findAll();
	}

	@Override
	public Flux<TransactionAcceptance> getAllProcessingAcceptance() {
		return transactionAcceptanceRepository.findAll().filter(t -> Constants.STATUS_PROCESSING.equalsIgnoreCase(t.getStatus()));
	}
	
	private Mono<TransactionAcceptance> save(TransactionRequest transactionRequest, TransactionAcceptance transactionAcceptance) {		
		// After validations to send to MS to register the transaction
		transactionAcceptance.setTransactionRequest(transactionRequest);
		transactionAcceptance.setStatus(Constants.STATUS_PROCESSING);
		return transactionAcceptanceRepository.save(transactionAcceptance)
			.flatMap(txAcceptance -> {
				transactionRequest.setStatus(Constants.STATUS_PROCESSING);
				transactionRequestRepository.save(transactionRequest).subscribe();
				// Send message
				kafkaTransactionAcceptanceProducer.sendMessage(transactionAcceptance);
				return Mono.just(txAcceptance);
			});
	}
	
	private Mono<Void> checkConditions(TransactionAcceptance transactionAcceptance) {
		return checkExistsAccount(transactionAcceptance)
				.mergeWith(checkTransactionRequestIsActive(transactionAcceptance.getTransactionRequest()))
				.then();
	}
	
	private Mono<Void> checkExistsAccount(TransactionAcceptance transactionAcceptance) {
		String transactionType = transactionAcceptance.getTransactionRequest().getTransactionType();
		
		if (Constants.TRANSACTION_TYPE_TRANSFER.equalsIgnoreCase(transactionType)) {
			// Valida que tengan cuenta bancaria
			return checkAccountNumberByIdentification(transactionAcceptance)
					//.mergeWith(checkAccountNumberByIdentification(buyerIdentification))
					.then();
			
		} else if (Constants.TRANSACTION_TYPE_YANQUI.equalsIgnoreCase(transactionType)) {
			// TODO: Validar que tengan cuenta en aplicativo Yanqui
			
		}
		
		return Mono.empty();
	}
	
	private Mono<Void> checkTransactionRequestIsActive(TransactionRequest transactionRequest) {
		String status = transactionRequest.getStatus();
		if (!Constants.STATUS_ACTIVE.equalsIgnoreCase(status)) {
			return Mono.error(new Exception("La solicitud de compra: " + transactionRequest.getId() + " no se encuentra activa"));
		}
		
		return Mono.empty();
	} 
	
	private Mono<Void> checkAccountNumberByIdentification(TransactionAcceptance transactionAcceptance) {
		String sellerIdentification = transactionAcceptance.getSellerIdentification();
		return accountProxy.findByDni(sellerIdentification)
			.switchIfEmpty(Mono.error(new Exception("Cuenta bancaria con identificación: " + sellerIdentification + " no existe")))
			.flatMap(account -> {
				transactionAcceptance.setAccountNumber(account.getAccountNumber());
				return Mono.empty();
			}).then();
	}
}
