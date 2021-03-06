/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.metamodel.model.domain.internal;

import org.hibernate.type.descriptor.java.JavaType;

public class PrimitiveBasicTypeImpl<J> extends BasicTypeImpl<J> {
	private final Class<J> javaTypeClass;

	public PrimitiveBasicTypeImpl(JavaType<J> javaType, Class<J> javaTypeClass) {
		super( javaType );
		assert javaTypeClass.isPrimitive();
		this.javaTypeClass = javaTypeClass;
	}

	@Override
	public Class<J> getJavaType() {
		return javaTypeClass;
	}
}
