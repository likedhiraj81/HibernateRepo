/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sql.results.jdbc.spi;

/**
 * Essentially processing options only for entity loading
 *
 * @author Steve Ebersole
 */
public interface JdbcValuesSourceProcessingOptions {
	Object getEffectiveOptionalObject();
	String getEffectiveOptionalEntityName();
	Object getEffectiveOptionalId();

	boolean shouldReturnProxies();
}
