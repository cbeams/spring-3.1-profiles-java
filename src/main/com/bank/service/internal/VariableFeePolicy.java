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

import com.bank.service.FeePolicy;

public class VariableFeePolicy implements FeePolicy {
	
	private double feePercentage;
	private double minimumFee;

	public void setMinimumFee(double minimumFee) {
		this.minimumFee = minimumFee;
	}
	
	public void setFeePercentage(double feePercentage) {
		this.feePercentage = feePercentage;
	}

	@Override
	public double calculateFee(double transferAmount) {
		double variableFee = transferAmount * (feePercentage/100);
		return variableFee > minimumFee ? variableFee : minimumFee;
	}

}
