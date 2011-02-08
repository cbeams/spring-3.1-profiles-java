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

package com.bank.config.code;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bank.domain.InsufficientFundsException;
import com.bank.repository.AccountRepository;
import com.bank.service.TransferService;

public class IntegrationTests {

	@Test
	public void transferTenDollars() throws InsufficientFundsException {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.getEnvironment().setDefaultProfiles("dev");
		//ctx.register(TransferConfig.class, JndiDataConfig.class, StandaloneDataConfig.class);
		ctx.scan("com.bank.config.code");
		ctx.refresh();

		TransferService transferService = ctx.getBean(TransferService.class);
		AccountRepository accountRepository = ctx.getBean(AccountRepository.class);

		assertThat(accountRepository.findById("A123").getBalance(), equalTo(100.00));
		assertThat(accountRepository.findById("C456").getBalance(), equalTo(0.00));

		transferService.transfer(10.00, "A123", "C456");

		assertThat(accountRepository.findById("A123").getBalance(), equalTo(90.00));
		assertThat(accountRepository.findById("C456").getBalance(), equalTo(10.00));
	}

}
