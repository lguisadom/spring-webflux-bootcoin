package com.nttdata.lagm.bootcoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.bootcoin.controller.dto.request.TransactionAcceptanceRqDto;
import com.nttdata.lagm.bootcoin.model.TransactionAcceptance;
import com.nttdata.lagm.bootcoin.repository.TransactionAcceptanceRepository;
import com.nttdata.lagm.bootcoin.repository.TransactionRequestRepository;
import com.nttdata.lagm.bootcoin.service.util.Constants;
import com.nttdata.lagm.bootcoin.service.util.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionAcceptanceServiceImpl implements TransactionAcceptanceService {
	
	@Autowired
	private TransactionAcceptanceRepository transactionAcceptanceRepository;
	
	@Autowired
	private TransactionRequestRepository transactionRequestRepository;
	
	@Override
	public Mono<TransactionAcceptance> create(TransactionAcceptanceRqDto transactionAcceptanceRqDto) {
		return transactionRequestRepository.findById(transactionAcceptanceRqDto.getIdTransactionRequest())
			.flatMap(transactionRequest -> {
				TransactionAcceptance transactionAcceptance = new TransactionAcceptance();
				transactionAcceptance.setDate(Util.getToday());
				transactionAcceptance.setStatus(Constants.STATUS_PROCESSING);
				transactionAcceptance.setTransactionRequest(transactionRequest);
				transactionRequest.setStatus(Constants.STATUS_PROCESSING);
				return transactionAcceptanceRepository.save(transactionAcceptance);		
			});
	}

	@Override
	public Flux<TransactionAcceptance> findAll() {
		return transactionAcceptanceRepository.findAll();
	}

	@Override
	public Flux<TransactionAcceptance> getAllProcessingAcceptance() {
		return transactionAcceptanceRepository.findAll().filter(t -> Constants.STATUS_PROCESSING.equalsIgnoreCase(t.getStatus()));
	}
}
