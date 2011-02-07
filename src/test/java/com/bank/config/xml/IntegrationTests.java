/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bank.config.xml;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.repository.AccountRepository;
import com.bank.service.TransferService;

public class IntegrationTests {

	@Test
	public void transfer() throws InsufficientFundsException {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setDefaultProfiles("dev");
		ctx.load("classpath:/com/bank/config/transfer-config.xml");
		ctx.refresh();

		TransferService transferService = ctx.getBean(TransferService.class);
		TransferReceipt receipt = transferService.transfer(10.00, "A123", "C456");

		assertThat(receipt.getFinalDestinationAccount().getBalance(), equalTo(10.00));

		AccountRepository accountRepository = ctx.getBean(AccountRepository.class);
		assertThat(accountRepository.findById("A123").getBalance(), equalTo(90.00));

	}

}
