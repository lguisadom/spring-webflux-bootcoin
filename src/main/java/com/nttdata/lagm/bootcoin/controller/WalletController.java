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

import com.nttdata.lagm.bootcoin.model.Wallet;
import com.nttdata.lagm.bootcoin.service.WalletService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
	@Autowired
	private WalletService walletService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	private Mono<Wallet> create(@RequestBody Wallet wallet) {
		return walletService.create(wallet);
	}
	
	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	private Flux<Wallet> findAll() {
		return walletService.findAll();
	}
}
	