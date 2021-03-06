/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id: Email.java 10977 2006-12-12 23:28:04Z steve.ebersole@jboss.com $
package org.hibernate.orm.test.collection.original;


/**
 * @author Gavin King
 */
public class Email {
	private String address;
	Email() {}
	public String getAddress() {
		return address;
	}
	public void setAddress(String type) {
		this.address = type;
	}
	public Email(String type) {
		this.address = type;
	}
	public boolean equals(Object that) {
		if ( !(that instanceof Email) ) return false;
		Email p = (Email) that;
		return this.address.equals(p.address);
	}
	public int hashCode() {
		return address.hashCode();
	}
}
