package com.nttdata.lagm.bootcoin.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.lagm.bootcoin.model.TransactionRequest;

public interface TransactionRequestRepository extends ReactiveMongoRepository<TransactionRequest, String>{

}
