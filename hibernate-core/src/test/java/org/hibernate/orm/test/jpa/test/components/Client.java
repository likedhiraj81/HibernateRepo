/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.jpa.test.components;
import java.io.Serializable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * The client domain entity
 *
 */
@Entity
public class Client implements Serializable {
	private int id;
	private Name name;

	public Client() {
	}

	public Client(int id, Name name) {
		this.id = id;
		this.name = name;
	}

	public Client(int id, String firstName, String lastName) {
		this( id, new Name( firstName, lastName ) );
	}

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Embedded
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}
}
