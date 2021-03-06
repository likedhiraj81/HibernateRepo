/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

// $Id:$

package org.hibernate.orm.test.annotations.derivedidentities;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

/**
 * @author Hardy Ferentschik
 */
@Entity
public class Dependent {
	@EmbeddedId
	DependentId id; // id attribute mapped by join column default

	@MapsId("empPK") // maps empPK attribute of embedded id
	@ManyToOne
	Employee employee;

	public Dependent() {
	}

	public Dependent(DependentId id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public DependentId getId() {
		return id;
	}
}


