/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.annotations.embeddables.nested.fieldaccess;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Thomas Vanstals
 * @author Steve Ebersole
 */
@Entity
@Access( value = AccessType.FIELD )
public class Customer {
	@Id
	@GeneratedValue( generator="increment" )
	@GenericGenerator( name = "increment", strategy = "increment" )
	private Long id;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<Investment> investments = new ArrayList<Investment>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Investment> getInvestments() {
		return investments;
	}

	public void setInvestments(List<Investment> investments) {
		this.investments = investments;
	}
}
