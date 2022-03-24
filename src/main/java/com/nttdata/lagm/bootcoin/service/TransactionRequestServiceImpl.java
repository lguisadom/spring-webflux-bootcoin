package com.nttdata.lagm.bootcoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.bootcoin.controller.dto.request.TransactionRequestRqDto;
import com.nttdata.lagm.bootcoin.model.TransactionRequest;
import com.nttdata.lagm.bootcoin.repository.TransactionRequestRepository;
import com.nttdata.lagm.bootcoin.service.util.Constants;
import com.nttdata.lagm.bootcoin.service.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionRequestServiceImpl implements TransactionRequestService {

	@Autowired
	private TransactionRequestRepository transactionRequestrepository;
	
	@Override
	public Mono<TransactionRequest> create(TransactionRequestRqDto transactionRequestRqDto) {
		TransactionRequest transactionRequest = new TransactionRequest();
		transactionRequest.setAmount(transactionRequestRqDto.getAmount());
		transactionRequest.setIdentification(transactionRequestRqDto.getIdentification());
		transactionRequest.setTransactionType(Util.getTransactionTypeDescription(transactionRequestRqDto.getTransactionType()));
		transactionRequest.setDate(Util.getToday());
		transactionRequest.setStatus(Constants.STATUS_ACTIVE);
		return transactionRequestrepository.save(transactionRequest);
	}

	@Override
	public Flux<TransactionRequest> findAll() {
		return transactionRequestrepository.findAll();
	}

	@Override
	public Flux<TransactionRequest> getAllActiveRequest() {
		return transactionRequestrepository.findAll().filter(t -> Constants.STATUS_ACTIVE.equalsIgnoreCase(t.getStatus()));
	}
}
