/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id: Down.java 4599 2004-09-26 05:18:27Z oneovthafew $
package org.hibernate.orm.test.legacy;

/**
 * @author Gavin King
 */
public class Down extends Up {

	private long value;

	public long getValue() {
		return value;
	}

	public void setValue(long l) {
		value = l;
	}

}
