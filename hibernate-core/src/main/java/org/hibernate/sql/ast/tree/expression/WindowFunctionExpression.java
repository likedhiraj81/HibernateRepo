/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sql.ast.tree.expression;

import org.hibernate.sql.ast.tree.predicate.Predicate;

/**
 * Models a window function expression at the SQL AST level.
 *
 * @author Christian Beikov
 */
public interface WindowFunctionExpression extends FunctionExpression {

	Predicate getFilter();

	Boolean getRespectNulls();

	Boolean getFromFirst();
}
