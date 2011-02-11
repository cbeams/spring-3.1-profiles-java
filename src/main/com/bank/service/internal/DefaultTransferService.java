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

package com.bank.service.internal;

import static java.lang.String.format;

import org.springframework.transaction.annotation.Transactional;

import com.bank.domain.Account;
import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.repository.AccountRepository;
import com.bank.service.FeePolicy;
import com.bank.service.TransferService;

public class DefaultTransferService implements TransferService {

	private final AccountRepository accountRepository;
	private final FeePolicy feePolicy;
	private double minimumTransferAmount = 1.00;

	public DefaultTransferService(AccountRepository accountRepository, FeePolicy feePolicy) {
		this.accountRepository = accountRepository;
		this.feePolicy = feePolicy;
	}

	@Override
	public void setMinimumTransferAmount(double minimumTransferAmount) {
		this.minimumTransferAmount = minimumTransferAmount;
	}

	@Override
	@Transactional
	public TransferReceipt transfer(double amount, String srcAcctId, String dstAcctId) throws InsufficientFundsException {
		if (amount < minimumTransferAmount)
			throw new IllegalArgumentException(
					format("transfer amount must be at least $%.2f", minimumTransferAmount));

		TransferReceipt receipt = new TransferReceipt();

		Account srcAcct = accountRepository.findById(srcAcctId);
		Account dstAcct = accountRepository.findById(dstAcctId);

		receipt.setInitialSourceAccount(srcAcct);
		receipt.setInitialDestinationAccount(dstAcct);

		double fee = feePolicy.calculateFee(amount);
		if (fee > 0)
			srcAcct.debit(fee);

		receipt.setTransferAmount(amount);
		receipt.setFeeAmount(fee);

		srcAcct.debit(amount);
		dstAcct.credit(amount);

		accountRepository.updateBalance(srcAcct);
		accountRepository.updateBalance(dstAcct);

		receipt.setFinalSourceAccount(srcAcct);
		receipt.setFinalDestinationAccount(dstAcct);

		return receipt;
	}

}
