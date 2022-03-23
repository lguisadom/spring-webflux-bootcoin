package com.nttdata.lagm.bootcoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.lagm.bootcoin.model.TransactionRequest;
import com.nttdata.lagm.bootcoin.service.TransactionRequestService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1/transaction/request")
public class TransactionRequestController {
	@Autowired
	private TransactionRequestService transactionRequestService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	private Mono<TransactionRequest> create(@RequestBody TransactionRequest transactionRequest) {
		return transactionRequestService.create(transactionRequest);
	}
	
	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	private Flux<TransactionRequest> findAll() {
		return transactionRequestService.findAll();
	}
	
	@GetMapping(value="/activeRequest", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	private Flux<TransactionRequest> getAllActiveRequest() {
		return transactionRequestService.getAllActiveRequest();
	}
}
