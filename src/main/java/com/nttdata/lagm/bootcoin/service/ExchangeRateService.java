package com.nttdata.lagm.bootcoin.service;

import com.nttdata.lagm.bootcoin.model.ExchangeRate;

import reactor.core.publisher.Mono;

public interface ExchangeRateService {
	Mono<ExchangeRate> create(ExchangeRate exchange);
	Mono<ExchangeRate> getExchangeRange();
}
