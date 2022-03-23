package com.nttdata.lagm.bootcoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.bootcoin.model.TransactionRequest;
import com.nttdata.lagm.bootcoin.repository.TransactionRequestRepository;
import com.nttdata.lagm.bootcoin.service.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionRequestServiceImpl implements TransactionRequestService {

	@Autowired
	private TransactionRequestRepository transactionRequestrepository;
	
	@Override
	public Mono<TransactionRequest> create(TransactionRequest transactionRequest) {
		transactionRequest.setDate(Util.getToday());
		transactionRequest.setCompleted(false);
		return transactionRequestrepository.save(transactionRequest);
	}

	@Override
	public Flux<TransactionRequest> findAll() {
		return transactionRequestrepository.findAll();
	}

	@Override
	public Flux<TransactionRequest> getAllActiveRequest() {
		return transactionRequestrepository.findAll().filter(t -> Boolean.FALSE.equals(t.getCompleted()));
	}
	
	

}
