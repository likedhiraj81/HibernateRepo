/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sql.ast.tree.from;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.internal.util.collections.CollectionHelper;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.SqlAstNode;

/**
 * The SQL AST from-clause node
 *
 * @author Steve Ebersole
 */
public class FromClause implements SqlAstNode {
	private final List<TableGroup> roots;

	public FromClause() {
		roots = new ArrayList<>();
	}

	public FromClause(int expectedNumberOfRoots) {
		roots = CollectionHelper.arrayList( expectedNumberOfRoots );
	}

	public List<TableGroup> getRoots() {
		return roots;
	}

	public void addRoot(TableGroup tableGroup) {
		roots.add( tableGroup );
	}

	public void visitRoots(Consumer<TableGroup> action) {
		roots.forEach( action );
	}

	public void visitTableGroups(Consumer<TableGroup> action) {
		queryTableGroups(
				tableGroup -> {
					action.accept( tableGroup );
					return null;
				}
		);
	}

	public <T> T queryTableGroups(Function<TableGroup, T> action) {
		for ( int i = 0; i < roots.size(); i++ ) {
			final T result = queryTableGroups( roots.get( i ), action );
			if ( result != null ) {
				return result;
			}
		}
		return null;
	}

	private <T> T queryTableGroups(TableGroup tableGroup, Function<TableGroup, T> action) {
		final T result = action.apply( tableGroup );
		if ( result != null ) {
			return result;
		}
		final List<TableGroupJoin> tableGroupJoins = tableGroup.getTableGroupJoins();
		for ( int i = 0; i < tableGroupJoins.size(); i++ ) {
			final T nestedResult = queryTableGroups( tableGroupJoins.get( i ).getJoinedGroup(), action );
			if ( nestedResult != null ) {
				return nestedResult;
			}
		}
		final List<TableGroupJoin> nestedTableGroupJoins = tableGroup.getNestedTableGroupJoins();
		for ( int i = 0; i < nestedTableGroupJoins.size(); i++ ) {
			final T nestedResult = queryTableGroups( nestedTableGroupJoins.get( i ).getJoinedGroup(), action );
			if ( nestedResult != null ) {
				return nestedResult;
			}
		}
		return null;
	}

	public void visitTableJoins(Consumer<TableJoin> action) {
		queryTableJoins(
				tableGroupJoin -> {
					action.accept( tableGroupJoin );
					return null;
				}
		);
	}

	public <T> T queryTableJoins(Function<TableJoin, T> action) {
		for ( int i = 0; i < roots.size(); i++ ) {
			T result = queryTableJoins( roots.get( i ), action );
			if ( result != null ) {
				return result;
			}
		}
		return null;
	}

	private <T> T queryTableJoins(TableGroup tableGroup, Function<TableJoin, T> action) {
		for ( TableReferenceJoin tableReferenceJoin : tableGroup.getTableReferenceJoins() ) {
			final T result = action.apply( tableReferenceJoin );
			if ( result != null ) {
				return result;
			}
		}

		final T result = queryTableJoins( tableGroup.getTableGroupJoins(), action );
		if ( result != null ) {
			return result;
		}
		return queryTableJoins( tableGroup.getNestedTableGroupJoins(), action );
	}

	private <T> T queryTableJoins(List<TableGroupJoin> tableGroupJoins, Function<TableJoin, T> action) {
		for ( int i = 0; i < tableGroupJoins.size(); i++ ) {
			final TableGroupJoin tableGroupJoin = tableGroupJoins.get( i );
			T result = action.apply( tableGroupJoin );
			if ( result != null ) {
				return result;
			}
			result = queryTableJoins( tableGroupJoin.getJoinedGroup(), action );
			if ( result != null ) {
				return result;
			}
		}
		return null;
	}

	public void visitTableGroupJoins(Consumer<TableGroupJoin> action) {
		queryTableGroupJoins(
				tableGroupJoin -> {
					action.accept( tableGroupJoin );
					return null;
				}
		);
	}

	public <T> T queryTableGroupJoins(Function<TableGroupJoin, T> action) {
		for ( int i = 0; i < roots.size(); i++ ) {
			final T result = queryTableGroupJoins( roots.get( i ), action );
			if ( result != null ) {
				return result;
			}
		}
		return null;
	}

	private <T> T queryTableGroupJoins(TableGroup tableGroup, Function<TableGroupJoin, T> action) {
		final T result = queryTableGroupJoins( tableGroup.getTableGroupJoins(), action );
		if ( result != null ) {
			return result;
		}
		return queryTableGroupJoins( tableGroup.getNestedTableGroupJoins(), action );
	}

	private <T> T queryTableGroupJoins(List<TableGroupJoin> tableGroupJoins, Function<TableGroupJoin, T> action) {
		for ( int i = 0; i < tableGroupJoins.size(); i++ ) {
			final TableGroupJoin tableGroupJoin = tableGroupJoins.get( i );
			T result = action.apply( tableGroupJoin );
			if ( result != null ) {
				return result;
			}
			result = queryTableGroupJoins( tableGroupJoin.getJoinedGroup(), action );
			if ( result != null ) {
				return result;
			}
		}
		return null;
	}

	public void visitTableReferences(Consumer<TableReference> action) {
		queryTableReferences(
				tableGroupJoin -> {
					action.accept( tableGroupJoin );
					return null;
				}
		);
	}

	public <T> T queryTableReferences(Function<TableReference, T> action) {
		for ( int i = 0; i < roots.size(); i++ ) {
			T result = queryTableReferences( roots.get( i ), action );
			if ( result != null ) {
				return result;
			}
		}
		return null;
	}

	private <T> T queryTableReferences(TableGroup tableGroup, Function<TableReference, T> action) {
		final T result = action.apply( tableGroup.getPrimaryTableReference() );
		if ( result != null ) {
			return result;
		}
		for ( TableReferenceJoin tableReferenceJoin : tableGroup.getTableReferenceJoins() ) {
			final T nestedResult = action.apply( tableReferenceJoin.getJoinedTableReference() );
			if ( nestedResult != null ) {
				return nestedResult;
			}
		}

		final T nestedResult = queryTableReferences( tableGroup.getTableGroupJoins(), action );
		if ( nestedResult != null ) {
			return nestedResult;
		}
		return queryTableReferences( tableGroup.getNestedTableGroupJoins(), action );
	}

	private <T> T queryTableReferences(List<TableGroupJoin> tableGroupJoins, Function<TableReference, T> action) {
		for ( int i = 0; i < tableGroupJoins.size(); i++ ) {
			final TableGroupJoin tableGroupJoin = tableGroupJoins.get( i );
			T result = queryTableReferences( tableGroupJoin.getJoinedGroup(), action );
			if ( result != null ) {
				return result;
			}
		}
		return null;
	}

	@Override
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitFromClause( this );
	}
}
