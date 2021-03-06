/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.orm.tooling.gradle.metamodel.model;

import java.io.BufferedWriter;

public interface MetamodelAttribute {
	String getName();

	Class<?> getAttributeJavaType();

	void renderJpaMembers(BufferedWriter writer);

	void renderNameConstant(BufferedWriter writer);
}
