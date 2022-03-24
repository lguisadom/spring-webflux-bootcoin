package com.nttdata.lagm.bootcoin.proxy;

import com.nttdata.lagm.bootcoin.model.account.BankAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountProxy {
	public Flux<BankAccount> findAll();
	public Mono<BankAccount> findById(String id);
	public Mono<BankAccount> findByAccountNumber(String accountNumber);
	public Mono<BankAccount> update(BankAccount bankAccount);
	public Mono<BankAccount> updateAmount(String id, String amount);
	public Flux<BankAccount> findByDni(String dni);
}
