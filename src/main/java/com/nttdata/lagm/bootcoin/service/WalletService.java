package com.nttdata.lagm.bootcoin.service;

import com.nttdata.lagm.bootcoin.model.Wallet;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletService {
	Mono<Wallet> create(Wallet wallet);
	Flux<Wallet> findAll();
}
