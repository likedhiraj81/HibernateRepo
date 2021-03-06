/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.community.dialect.sequence;

import org.hibernate.MappingException;
import org.hibernate.dialect.sequence.ANSISequenceSupport;
import org.hibernate.dialect.sequence.SequenceSupport;

/**
 * Sequence support for {@link org.hibernate.community.dialect.FirebirdDialect}
 * on Firebird 3.
 *
 * @author Gavin King
 */
public class FirebirdSequenceSupport extends ANSISequenceSupport {

	public static final SequenceSupport INSTANCE = new FirebirdSequenceSupport() {
		@Override
		public String getCreateSequenceString(String sequenceName, int initialValue, int incrementSize) {
			// NOTE Firebird 3 has an 'off by increment' bug, see
			// http://tracker.firebirdsql.org/browse/CORE-6084
			if (initialValue == 1 && incrementSize == 1) {
				// Workaround for initial value and increment 1
				// This workaround also works fine in Firebird 4, so we don't need to add yet another specialization
				return getCreateSequenceString( sequenceName );
			}
			return super.getCreateSequenceString( sequenceName, initialValue, incrementSize);
		}
	};

	public static final SequenceSupport LEGACY_INSTANCE = new FirebirdSequenceSupport() {
		@Override
		public String[] getCreateSequenceStrings(String sequenceName, int initialValue, int incrementSize) {
			return initialValue == 1
					? new String[] { getCreateSequenceString(sequenceName) }
					: new String[] {
							getCreateSequenceString( sequenceName ),
							"alter sequence " + sequenceName
									+ " restart with " + (initialValue-incrementSize)
					};
		}
		@Override
		public String getSequenceNextValString(String sequenceName, int increment) {
			return increment == 1
					? getSequenceNextValString( sequenceName )
					: "select gen_id(" + sequenceName + "," + increment + ") from rdb$database";
		}
	};

	@Override
	public String getFromDual() {
		return " from rdb$database";
	}

	@Override
	public String getSelectSequencePreviousValString(String sequenceName) throws MappingException {
		return "gen_id(" + sequenceName + ",0)";
	}

}
