package com.nttdata.lagm.bootcoin.service.util;

import java.time.LocalDateTime;
import java.util.UUID;

public class Util {

	public static LocalDateTime getToday() {
		return LocalDateTime.now();
	}
	
	public static String getTransactionTypeDescription(Integer transactionTypeId) {
		String description = "";
		switch (transactionTypeId) {
			case 1:
				description = Constants.TRANSACTION_TYPE_YANQUI;
				break;
				
			case 2:
				description = Constants.TRANSACTION_TYPE_TRANSFER;
				break;
				
			default:
				description = Constants.TRANSACTION_TYPE_UNDEFINED;
				break;
		}
		
		return description;
	}
	
	public static String generateTransactionId() {
		return UUID.randomUUID().toString();
	}
}
