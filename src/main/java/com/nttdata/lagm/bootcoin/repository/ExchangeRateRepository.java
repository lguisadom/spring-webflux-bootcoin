package com.nttdata.lagm.bootcoin.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.lagm.bootcoin.model.ExchangeRate;
import com.nttdata.lagm.bootcoin.model.TransactionAcceptance;

import reactor.core.publisher.Mono;

@Repository
public interface ExchangeRateRepository extends ReactiveMongoRepository<ExchangeRate, String> {
	Mono<ExchangeRate> findByDate(LocalDateTime date);
	Mono<ExchangeRate> findTopByOrderByDateDesc();
}
