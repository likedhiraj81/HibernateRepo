/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.immutable.entitywithmutablecollection.noninverse;

import org.hibernate.orm.test.immutable.entitywithmutablecollection.AbstractEntityWithOneToManyTest;

import org.hibernate.testing.orm.junit.DomainModel;

/**
 * @author Gail Badner
 */
@DomainModel(
		xmlMappings = "org/hibernate/orm/test/immutable/entitywithmutablecollection/noninverse/ContractVariationVersioned.hbm.xml"
)
public class VersionedEntityWithNonInverseOneToManyTest extends AbstractEntityWithOneToManyTest {
}
