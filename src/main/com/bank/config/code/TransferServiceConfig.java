package com.bank.config.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bank.repository.AccountRepository;
import com.bank.repository.internal.JdbcAccountRepository;
import com.bank.service.FeePolicy;
import com.bank.service.TransferService;
import com.bank.service.internal.DefaultTransferService;
import com.bank.service.internal.ZeroFeePolicy;

@Configuration
public class TransferServiceConfig {

	@Autowired DataConfig dataConfig;

	@Bean
	public TransferService transferService() {
		return new DefaultTransferService(accountRepository(), feePolicy());
	}

	@Bean
	public AccountRepository accountRepository() {
		return new JdbcAccountRepository(dataConfig.dataSource());
	}

	@Bean
	public FeePolicy feePolicy() {
		return new ZeroFeePolicy();
	}

}
