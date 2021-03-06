/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.query.sqm.internal;

import org.hibernate.query.hql.spi.SqmCreationProcessingState;
import org.hibernate.query.hql.spi.SqmCreationState;
import org.hibernate.query.hql.spi.SqmQuerySpecCreationProcessingState;
import org.hibernate.query.sqm.tree.select.SqmSelectQuery;

/**
 * Models the state related to parsing a sqm spec.  As a "linked list" to account for
 * subqueries
 *
 * @author Steve Ebersole
 * @author Andrea Boriero
 */
public class SqmQuerySpecCreationProcessingStateStandardImpl
		extends SqmCreationProcessingStateImpl
		implements SqmQuerySpecCreationProcessingState {

	private final SqmCreationProcessingState parentState;

	public SqmQuerySpecCreationProcessingStateStandardImpl(
			SqmCreationProcessingState parentState,
			SqmSelectQuery<?> processingQuery,
			SqmCreationState creationState) {
		super( processingQuery, creationState );
		this.parentState = parentState;
	}

	@Override
	public SqmCreationProcessingState getParentProcessingState() {
		return parentState;
	}

	@Override
	public SqmSelectQuery<?> getProcessingQuery() {
		return (SqmSelectQuery<?>) super.getProcessingQuery();
	}
}
