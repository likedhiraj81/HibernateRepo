/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.envers.integration.inheritance.mixed.entities;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class ActivityId implements Serializable {
	private Integer id;
	private Integer id2;

	public ActivityId() {
	}

	public ActivityId(int i, int i1) {
		id = i;
		id2 = i1;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId2() {
		return id2;
	}

	public void setId2(Integer id2) {
		this.id2 = id2;
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) {
			return true;
		}
		if ( !(obj instanceof ActivityId) ) {
			return false;
		}
		ActivityId id = (ActivityId) obj;
		return getId().equals( id.getId() ) && getId2().equals( id.getId2() );
	}

	@Override
	public int hashCode() {
		int result = getId().hashCode();
		result = 31 * result + getId2().hashCode();
		return result;
	}
}
