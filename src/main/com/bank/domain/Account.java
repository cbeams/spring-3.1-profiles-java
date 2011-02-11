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

public class Account {

	private final String id;
	private double balance;

	public Account(String id, double initialBalance) {
		this.id = id;
		this.balance = initialBalance;
	}

	public void debit(double amount) throws InsufficientFundsException {
		assertValid(amount);

		if (amount > balance)
			throw new InsufficientFundsException(this, amount);

		balance -= amount;
	}

	public void credit(double amount) {
		assertValid(amount);
		
		balance += amount;
	}
	
	public String getId() {
		return id;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	private void assertValid(double amount) {
		if (!(amount > 0.00))
			throw new IllegalArgumentException("amount must be greater than zero");
	}

	public static Account copy(Account account) {
		return new Account(account.getId(), account.getBalance());
	}

}
