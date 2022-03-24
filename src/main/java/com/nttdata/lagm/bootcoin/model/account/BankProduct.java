package com.nttdata.lagm.bootcoin.model.account;

import com.nttdata.lagm.bootcoin.model.customer.Customer;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class BankProduct {
	private String id;
	private String accountNumber;
	private String cci;
	private Customer customer;
	private String amount;
	private Boolean status = true;
}