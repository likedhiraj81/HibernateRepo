/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.metamodel.spi;

import org.hibernate.Incubating;
import org.hibernate.engine.spi.SessionFactoryImplementor;

/**
 *
 * @author Christian Beikov
 */
@Incubating
public interface ValueAccess {
	Object[] getValues();

	default <T> T getValue(int i, Class<T> clazz) {
		return clazz.cast( getValues()[i] );
	}

	default Object getOwner() {
		return null;
	}
}
