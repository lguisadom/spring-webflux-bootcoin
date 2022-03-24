package com.nttdata.lagm.bootcoin.controller.dto.request;

import lombok.Data;

@Data
public class TransactionAcceptanceRqDto {
	private String sellerIdentification;
	private String idTransactionRequest;
}
