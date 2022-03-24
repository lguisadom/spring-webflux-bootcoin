package com.nttdata.lagm.bootcoin.controller.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionRequestRqDto {
	private String identification;
	private BigDecimal amount;
	private Integer transactionType;
}
