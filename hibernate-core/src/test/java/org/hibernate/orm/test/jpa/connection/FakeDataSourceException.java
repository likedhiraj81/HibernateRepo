/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id$
package org.hibernate.orm.test.jpa.connection;


/**
 * @author Emmanuel Bernard
 */
public class FakeDataSourceException extends RuntimeException {
	public FakeDataSourceException(String message) {
		super( message );
	}
}
