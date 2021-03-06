/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sql.ast;

/**
 * @author Steve Ebersole
 */
public enum SqlAstJoinType {
	/**
	 * Represents an inner join.
	 */
	INNER( "" ),

	/**
	 * Represents a left outer join.
	 */
	LEFT( "left " ),

	/**
	 * Represents a right outer join.
	 */
	RIGHT( "right " ),

	/**
	 * Represents a cross join (aka a cartesian product).
	 */
	CROSS( "cross " ),

	/**
	 * Represents a full join.
	 */
	FULL( "full " );

	private final String text;

	SqlAstJoinType(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	public String getText() {
		return text;
	}
}
