package com.nttdata.lagm.bootcoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.bootcoin.model.ExchangeRate;
import com.nttdata.lagm.bootcoin.repository.ExchangeRateRepository;
import com.nttdata.lagm.bootcoin.service.util.Util;

import reactor.core.publisher.Mono;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	@Override
	public Mono<ExchangeRate> create(ExchangeRate exchange) {
		exchange.setDate(Util.getToday());
		return exchangeRateRepository.save(exchange);
	}

	@Override
	public Mono<ExchangeRate> getExchangeRange() {
		return exchangeRateRepository.findTopByOrderByDateDesc();
	}

}
