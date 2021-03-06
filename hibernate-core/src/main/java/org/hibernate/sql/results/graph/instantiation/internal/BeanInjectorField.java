/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sql.results.graph.instantiation.internal;

import java.lang.reflect.Field;

import org.hibernate.query.sqm.sql.internal.InstantiationException;

/**
 * @author Steve Ebersole
 */
class BeanInjectorField<T> implements BeanInjector<T> {
	private final Field field;

	public BeanInjectorField(Field field) {
		this.field = field;
	}

	@Override
	public void inject(T target, Object value) {
		try {
			field.set( target, value );
		}
		catch (Exception e) {
			throw new InstantiationException( "Error performing the dynamic instantiation", e );
		}
	}
}
