/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.annotations.collectionelement.embeddables.withcustomenumdef;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Steve Ebersole
 */
@Entity
@Table(name="`Query`")
public class Query {
	@Id
	@GeneratedValue( generator = "increment" )
	@GenericGenerator( name = "increment", strategy = "increment" )
	private Long id;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Location> includedLocations = new HashSet<Location>();

	public Query() {
	}

	public Query(Location... locations) {
		Collections.addAll( includedLocations, locations );
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Location> getIncludedLocations() {
		return includedLocations;
	}

	public void setIncludedLocations(Set<Location> includedLocations) {
		this.includedLocations = includedLocations;
	}
}
