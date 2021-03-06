/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.orm.test.annotations.type.dynamicparameterized;

import java.util.Date;

import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Daniel Gredler
 */
@DomainModel(
		annotatedClasses = { AbstractEntity.class, Entity1.class, Entity2.class }
)
@SessionFactory
public class DynamicParameterizedTypeTest {

	@Test
	public void testParameterValues(SessionFactoryScope scope) {
		scope.inTransaction(
				session -> {
					Entity1 entity1 = new Entity1();
					entity1.id = new Date( 0 );

					Entity2 entity2 = new Entity2();
					entity2.id = new Date( 0 );

					session.persist( entity1 );
					session.persist( entity2 );
					session.flush();
					session.clear();

					entity1 = session.byId( Entity1.class ).load( entity1.id );
					entity2 = session.byId( Entity2.class ).load( entity2.id );

					assertEquals( "ENTITY1.PROP1", entity1.entity1_Prop1 );
					assertEquals( "ENTITY1.PROP2", entity1.entity1_Prop2 );
					assertEquals( "ENTITY1.PROP3.FOO", entity1.entity1_Prop3 );
					assertEquals( "ENTITY1.PROP4.BAR", entity1.entity1_Prop4 );
					assertEquals( "ENTITY1.PROP5", entity1.entity1_Prop5 );
					assertEquals( "ENTITY1.PROP6", entity1.entity1_Prop6 );

					assertEquals( "ENTITY2.PROP1", entity2.entity2_Prop1 );
					assertEquals( "ENTITY2.PROP2", entity2.entity2_Prop2 );
					assertEquals( "ENTITY2.PROP3", entity2.entity2_Prop3 );
					assertEquals( "ENTITY2.PROP4", entity2.entity2_Prop4 );
					assertEquals( "ENTITY2.PROP5.BLAH", entity2.entity2_Prop5 );
					assertEquals( "ENTITY2.PROP6.YEAH", entity2.entity2_Prop6 );
				}
		);
	}
}
