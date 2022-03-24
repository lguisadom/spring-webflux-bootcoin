package com.nttdata.lagm.bootcoin.kafka.message;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TransactionAcceptanceMessage implements Serializable {
	private String id;
	private String sellerIdentification;
	private TransactionRequestMessage transactionRequestMessage;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime date;
	private String status;
}
