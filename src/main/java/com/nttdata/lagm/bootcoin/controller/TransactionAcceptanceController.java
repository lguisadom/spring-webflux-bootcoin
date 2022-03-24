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

import com.nttdata.lagm.bootcoin.controller.dto.request.TransactionAcceptanceRqDto;
import com.nttdata.lagm.bootcoin.model.TransactionAcceptance;
import com.nttdata.lagm.bootcoin.service.TransactionAcceptanceService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1/transaction/acceptance")
public class TransactionAcceptanceController {

	@Autowired
	private TransactionAcceptanceService transactionAcceptanceService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	private Mono<TransactionAcceptance> create(@RequestBody TransactionAcceptanceRqDto transactionAcceptanceRqDto) {
		return transactionAcceptanceService.create(transactionAcceptanceRqDto);
	}
	
	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	private Flux<TransactionAcceptance> findAll() {
		return transactionAcceptanceService.findAll();
	}
	
	@GetMapping(value="/processing", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	private Flux<TransactionAcceptance> getAllProcessingRequest() {
		return transactionAcceptanceService.getAllProcessingAcceptance();
	}
}
