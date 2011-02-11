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

package com.bank.domain;

import static java.lang.String.format;

@SuppressWarnings("serial")
public class InsufficientFundsException extends Exception {
	private final Account targetAccount;
	private final double attemptedAmount;
	
	public InsufficientFundsException(Account targetAccount, double attemptedAmount) {
		this.targetAccount = Account.copy(targetAccount);
		this.attemptedAmount = attemptedAmount;
	}

	public String getTargetAccountId() {
		return targetAccount.getId();
	}

	public double getTargetAccountBalance() {
		return targetAccount.getBalance();
	}

	public double getAttemptedAmount() {
		return attemptedAmount;
	}
	
	public double getOverage() {
		return getAttemptedAmount() - getTargetAccountBalance();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder()
			.append(format("Failed to transfer $%.2f from account %s. " +
					"Reason: insufficient funds\n", getAttemptedAmount(), getTargetAccountId()))
			.append(format("\tcurrent balance for target account is $%.2f\n", getTargetAccountBalance()))
			.append(format("\ttransfer overage is $%.2f\n", getOverage()));
		return sb.toString();
	}
}
