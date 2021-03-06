/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.annotations.mappedsuperclass.intermediate;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;


/**
 * Represents the intermediate mapped superclass in the hierarchy.
 *
 * @author Saša Obradović
 */
@MappedSuperclass
public abstract class SavingsAccountBase extends Account {
	@Column(name = "SAVACC_WITHDRAWALLIMIT",
			precision = 8, scale = 2)
	private BigDecimal withdrawalLimit;

	protected SavingsAccountBase() {
	}

	protected SavingsAccountBase(String accountNumber, BigDecimal withdrawalLimit) {
		super( accountNumber );
		this.withdrawalLimit = withdrawalLimit;
	}

	public BigDecimal getWithdrawalLimit() {
		return withdrawalLimit;
	}

	public void setWithdrawalLimit(BigDecimal withdrawalLimit) {
		this.withdrawalLimit = withdrawalLimit;
	}
}
