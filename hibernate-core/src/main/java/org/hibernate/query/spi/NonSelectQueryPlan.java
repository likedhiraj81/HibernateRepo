/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.query.spi;

/**
 * @author Steve Ebersole
 */
public interface NonSelectQueryPlan extends QueryPlan {
	int executeUpdate(DomainQueryExecutionContext executionContext);
}
