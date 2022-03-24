package com.nttdata.lagm.bootcoin.service;

import com.nttdata.lagm.bootcoin.dto.request.TransactionAcceptanceRqDto;
import com.nttdata.lagm.bootcoin.model.TransactionAcceptance;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionAcceptanceService {
	Mono<TransactionAcceptance> create(TransactionAcceptanceRqDto transactionAcceptanceRqDto);
	Flux<TransactionAcceptance> findAll();
	Flux<TransactionAcceptance> getAllProcessingAcceptance();
}
