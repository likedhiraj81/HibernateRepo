/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.orm.test.bytecode.enhancement.lazy.proxy.inlinedirtychecking;

import org.hibernate.bytecode.enhance.spi.UnloadedClass;
import org.hibernate.bytecode.enhance.spi.UnloadedField;

import org.hibernate.testing.bytecode.enhancement.EnhancerTestContext;

/**
 * @author Andrea Boriero
 */
public class NoDirtyCheckEnhancementContext extends EnhancerTestContext {
	@Override
	public boolean doDirtyCheckingInline(UnloadedClass classDescriptor) {
		return false;
	}

	@Override
	public boolean doExtendedEnhancement(UnloadedClass classDescriptor) {
		return false;
	}

	@Override
	public boolean doBiDirectionalAssociationManagement(UnloadedField field) {
		return false;
	}
}
