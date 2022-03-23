package com.nttdata.lagm.bootcoin.service;

import com.nttdata.lagm.bootcoin.model.TransactionRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRequestService {
	Mono<TransactionRequest> create(TransactionRequest transaction);
	Flux<TransactionRequest> findAll();
}
