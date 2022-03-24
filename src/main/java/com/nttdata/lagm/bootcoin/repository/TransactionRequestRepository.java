package com.nttdata.lagm.bootcoin.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.lagm.bootcoin.model.TransactionRequest;

@Repository
public interface TransactionRequestRepository extends ReactiveMongoRepository<TransactionRequest, String>{
}
