package com.nttdata.lagm.bootcoin.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionRequestRqDto {
	private String identification;
	private BigDecimal amount;
	private Integer transactionType;
}
