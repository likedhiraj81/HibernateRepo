/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.immutable.entitywithmutablecollection.inverse;

import org.hibernate.orm.test.immutable.entitywithmutablecollection.AbstractEntityWithOneToManyTest;

import org.hibernate.testing.orm.junit.DomainModel;

/**
 * @author Gail Badner
 */
@DomainModel(
		xmlMappings = "org/hibernate/orm/test/immutable/entitywithmutablecollection/inverse/ContractVariationVersioned.hbm.xml"
)
public class VersionedEntityWithInverseOneToManyTest extends AbstractEntityWithOneToManyTest {

	@Override
	protected boolean checkUpdateCountsAfterAddingExistingElement() {
		return false;
	}

	@Override
	protected boolean checkUpdateCountsAfterRemovingElementWithoutDelete() {
		return false;
	}
}
