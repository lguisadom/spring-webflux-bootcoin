package com.nttdata.lagm.bootcoin.service;

import com.nttdata.lagm.bootcoin.controller.dto.request.TransactionAcceptanceRqDto;
import com.nttdata.lagm.bootcoin.model.TransactionAcceptance;

import reactor.core.publisher.Mono;

public interface TransactionAcceptanceService {
	Mono<TransactionAcceptance> create(TransactionAcceptanceRqDto transactionAcceptanceRqDto);
}
