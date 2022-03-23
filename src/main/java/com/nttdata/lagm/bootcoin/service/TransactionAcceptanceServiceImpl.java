package com.nttdata.lagm.bootcoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.bootcoin.controller.dto.request.TransactionAcceptanceRqDto;
import com.nttdata.lagm.bootcoin.model.TransactionAcceptance;
import com.nttdata.lagm.bootcoin.repository.TransactionAcceptanceRepository;
import com.nttdata.lagm.bootcoin.repository.TransactionRequestRepository;
import com.nttdata.lagm.bootcoin.service.util.Util;

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
				transactionAcceptance.setCompleted(false);
				transactionAcceptance.setTransactionRequest(transactionRequest);
				return transactionAcceptanceRepository.save(transactionAcceptance);		
			});
	}
}
