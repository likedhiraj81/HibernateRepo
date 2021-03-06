/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id: $

package org.hibernate.orm.test.event.collection;


/**
 *
 * @author Gail Badner
 */
public class ChildValue implements Child {
	private String name;

	public ChildValue() {
	}

	public ChildValue(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object otherChild) {
		if ( this == otherChild ) {
			return true;
		}
		if ( !( otherChild instanceof ChildValue ) ) {
			return false;
		}
		return name.equals( ( ( ChildValue ) otherChild ).name );
	}

	public int hashCode() {
		return name.hashCode();
	}
}
