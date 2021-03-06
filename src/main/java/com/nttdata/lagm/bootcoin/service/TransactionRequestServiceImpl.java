package com.nttdata.lagm.bootcoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.bootcoin.dto.request.TransactionRequestRqDto;
import com.nttdata.lagm.bootcoin.model.TransactionRequest;
import com.nttdata.lagm.bootcoin.proxy.AccountProxy;
import com.nttdata.lagm.bootcoin.repository.TransactionRequestRepository;
import com.nttdata.lagm.bootcoin.repository.WalletRepository;
import com.nttdata.lagm.bootcoin.util.Constants;
import com.nttdata.lagm.bootcoin.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionRequestServiceImpl implements TransactionRequestService {

	@Autowired
	private TransactionRequestRepository transactionRequestRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private AccountProxy accountProxy;
	
	@Override
	public Mono<TransactionRequest> create(TransactionRequestRqDto transactionRequestRqDto) {
		TransactionRequest transactionRequest = new TransactionRequest();
		transactionRequest.setAmount(transactionRequestRqDto.getAmount());
		transactionRequest.setIdentification(transactionRequestRqDto.getIdentification());
		transactionRequest.setTransactionType(Util.getTransactionTypeDescription(transactionRequestRqDto.getTransactionType()));
		transactionRequest.setDate(Util.getToday());
		transactionRequest.setStatus(Constants.STATUS_ACTIVE);
		return checkConditions(transactionRequest)
				.then(save(transactionRequest));
	}

	@Override
	public Flux<TransactionRequest> findAll() {
		return transactionRequestRepository.findAll();
	}

	@Override
	public Flux<TransactionRequest> getAllActiveRequest() {
		return transactionRequestRepository.findAll().filter(t -> Constants.STATUS_ACTIVE.equalsIgnoreCase(t.getStatus()));
	}
	
	private Mono<TransactionRequest> save(TransactionRequest transactionRequest) {
		return transactionRequestRepository.save(transactionRequest);
	}
	
	private Mono<Void> checkConditions(TransactionRequest transactionRequest) {
		return checkExistsAccount(transactionRequest)
				.mergeWith(checkExistsBootcoinWallet(transactionRequest))
				.then();
	}
	
	private Mono<Void> checkExistsBootcoinWallet(TransactionRequest transactionRequest) {
		String buyerIdentification = transactionRequest.getIdentification();
		return walletRepository.findAll().filter(wallet -> wallet.getIdentification().equalsIgnoreCase(buyerIdentification))
			.switchIfEmpty(Mono.error(new Exception("El comprador con n??mero de identificaci??n: " + buyerIdentification + " no tiene billeteras")))
			.then();
	}
	
	private Mono<Void> checkExistsAccount(TransactionRequest transactionRequest) {
		String transactionType = transactionRequest.getTransactionType();
		
		if (Constants.TRANSACTION_TYPE_TRANSFER.equalsIgnoreCase(transactionType)) {
			// Valida que tenga cuenta bancaria
			return checkAccountNumberByIdentification(transactionRequest)
					.then();
		} else if (Constants.TRANSACTION_TYPE_YANQUI.equalsIgnoreCase(transactionType)) {
			// TODO: Validar que tengan cuenta en aplicativo Yanqui
			
		}
		
		return Mono.empty();
	}
	
	private Mono<Void> checkAccountNumberByIdentification(TransactionRequest transactionRequest) {
		String identification = transactionRequest.getIdentification();
		return accountProxy.findByDni(identification)
			.switchIfEmpty(Mono.error(new Exception("Cuenta bancaria con identificaci??n: " + identification + " no existe")))
			.flatMap(account -> {
				transactionRequest.setAccountNumber(account.getAccountNumber());
				return Mono.empty();
			}).then();
	}
}
