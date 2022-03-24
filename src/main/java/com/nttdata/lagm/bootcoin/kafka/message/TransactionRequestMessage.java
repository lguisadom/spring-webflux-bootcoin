package com.nttdata.lagm.bootcoin.kafka.message;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TransactionRequestMessage {
	private String id;
	private String identification;
	private BigDecimal amount;
	private String transactionType;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime date;
	private String status;
}
