/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.insertordering;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import org.hibernate.annotations.BatchSize;

import org.hibernate.testing.TestForIssue;
import org.junit.After;
import org.junit.jupiter.api.Test;

/**
 * @author Vlad Mihalcea
 */
@TestForIssue(jiraKey = "HHH-9864")
public class InsertOrderingWithJoinedTableMultiLevelInheritance extends BaseInsertOrderingTest {

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] {
				Address.class,
				Person.class,
				SpecialPerson.class,
				AnotherPerson.class,
				President.class,
				Office.class
		};
	}

	@Test
	public void testBatchingAmongstSubClasses() {
		sessionFactoryScope().inTransaction( session -> {
			int iterations = 2;
			for ( int i = 0; i < iterations; i++ ) {
				final President president = new President();
				president.addAddress( new Address() );
				session.persist( president );

				final AnotherPerson anotherPerson = new AnotherPerson();
				Office office = new Office();
				session.persist( office );
				anotherPerson.office = office;
				session.persist( anotherPerson );

				final Person person = new Person();
				session.persist( person );

				final SpecialPerson specialPerson = new SpecialPerson();
				specialPerson.addAddress( new Address() );
				session.persist( specialPerson );
			}
			clearBatches();
		} );

		verifyPreparedStatementCount( 17 );
	}

	@After
	protected void cleanupTestData() {
		sessionFactoryScope().inTransaction( session -> {
			session.createQuery( "delete Address" ).executeUpdate();
			session.createQuery( "delete Person" ).executeUpdate();
			session.createQuery( "delete SpecialPerson" ).executeUpdate();
			session.createQuery( "delete AnotherPerson" ).executeUpdate();
			session.createQuery( "delete Office" ).executeUpdate();
			session.createQuery( "delete President" ).executeUpdate();
		} );
	}

	@Entity(name = "Address")
	@Table(name = "ADDRESS")
	public static class Address {
		@Id
		@Column(name = "ID", nullable = false)
		@SequenceGenerator(name = "ID", sequenceName = "ADDRESS_SEQ")
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID")
		private Long id;
	}

	@Entity(name = "Office")
	public static class Office {
		@Id
		@Column(name = "ID", nullable = false)
		@SequenceGenerator(name = "ID_2", sequenceName = "ADDRESS_SEQ")
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_2")
		private Long id;
	}

	@Entity(name = "Person")
	@Table(name = "PERSON")
	@Inheritance(strategy = InheritanceType.JOINED)
	@DiscriminatorColumn(name = "CLASSINDICATOR", discriminatorType = DiscriminatorType.INTEGER)
	public static class Person {
		@Id
		@Column(name = "ID", nullable = false)
		@SequenceGenerator(name = "ID_3", sequenceName = "PERSON_SEQ")
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_3")
		private Long id;

	}

	@Entity(name = "SpecialPerson")
	public static class SpecialPerson extends Person {
		@Column(name = "special")
		private String special;

		@OneToMany(orphanRemoval = true, cascade = {
				CascadeType.PERSIST,
				CascadeType.REMOVE
		})
		@JoinColumn(name = "PERSONID", referencedColumnName = "ID", nullable = false, updatable = false)
		@BatchSize(size = 100)
		private Set<Address> addresses = new HashSet<>();

		public void addAddress(Address address) {
			this.addresses.add( address );
		}
	}

	@Entity(name = "AnotherPerson")
	public static class AnotherPerson extends Person {
		private boolean working;

		@ManyToOne
		private Office office;
	}

	@Entity(name = "President")
	public static class President extends SpecialPerson {

		@Column(name = "salary")
		private BigDecimal salary;
	}
}
