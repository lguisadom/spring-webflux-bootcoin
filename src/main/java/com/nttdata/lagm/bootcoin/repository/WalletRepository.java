package com.nttdata.lagm.bootcoin.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.lagm.bootcoin.model.Wallet;

import reactor.core.publisher.Mono;

@Repository
public interface WalletRepository extends ReactiveMongoRepository<Wallet, String> {
}
