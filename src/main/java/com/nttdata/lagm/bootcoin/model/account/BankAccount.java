package com.nttdata.lagm.bootcoin.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BankAccount extends BankProduct {
	private BankAccountType bankAccountType;
	private String maintenanceFee;
	private Integer maxLimitMonthlyMovements;
	private Integer dayAllowed;
}
