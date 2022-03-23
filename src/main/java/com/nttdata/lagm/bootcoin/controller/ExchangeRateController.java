package com.nttdata.lagm.bootcoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.lagm.bootcoin.model.ExchangeRate;
import com.nttdata.lagm.bootcoin.service.ExchangeRateService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1/exchange")
public class ExchangeRateController {
	
	@Autowired
	private ExchangeRateService exchangeRateService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	private Mono<ExchangeRate> create(@RequestBody ExchangeRate exchangeRate) {
		return exchangeRateService.create(exchangeRate);
	}
}
