/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.idprops;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * {@inheritDoc}
 *
 * @author Steve Ebersole
 */
public class Order {
	private Long number;
	private Date placed;
	private Person orderee;

	private Set lineItems = new HashSet();

	public Order() {
	}

	public Order(Long number, Person orderee) {
		this.number = number;
		this.orderee = orderee;
		this.placed = new Date();
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Date getPlaced() {
		return placed;
	}

	public void setPlaced(Date placed) {
		this.placed = placed;
	}

	public Person getOrderee() {
		return orderee;
	}

	public void setOrderee(Person orderee) {
		this.orderee = orderee;
	}


	public Set getLineItems() {
		return lineItems;
	}

	public void setLineItems(Set lineItems) {
		this.lineItems = lineItems;
	}
}
