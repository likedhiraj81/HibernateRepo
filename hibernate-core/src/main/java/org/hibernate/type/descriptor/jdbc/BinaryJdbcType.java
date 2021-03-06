/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.type.descriptor.jdbc;
import java.sql.Types;

/**
 * Descriptor for {@link Types#BINARY BINARY} handling.
 *
 * @author Steve Ebersole
 */
public class BinaryJdbcType extends VarbinaryJdbcType {
	public static final BinaryJdbcType INSTANCE = new BinaryJdbcType();

	public BinaryJdbcType() {
	}

	@Override
	public int getJdbcTypeCode() {
		return Types.BINARY;
	}
}
