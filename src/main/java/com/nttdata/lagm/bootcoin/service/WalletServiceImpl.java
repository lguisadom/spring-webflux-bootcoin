package com.nttdata.lagm.bootcoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.bootcoin.model.Wallet;
import com.nttdata.lagm.bootcoin.repository.WalletRepository;
import com.nttdata.lagm.bootcoin.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	private WalletRepository walletRepository;
	
	@Override
	public Mono<Wallet> create(Wallet wallet) {
		wallet.setDate(Util.getToday());
		return walletRepository.save(wallet);
	}

	@Override
	public Flux<Wallet> findAll() {
		return walletRepository.findAll();
	}

}
