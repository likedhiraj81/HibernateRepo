/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.jpa.criteria;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Animal
 *
 */
@Entity
@Table( name = "ANIMAL" )
public class Animal {
	private Long id;
	private Animal mother;
	private Animal father;
	private String name;
	private Date born;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	public Animal getMother() {
		return mother;
	}

	public void setMother(Animal mother) {
		this.mother = mother;
	}

	@ManyToOne
	public Animal getFather() {
		return father;
	}

	public void setFather(Animal father) {
		this.father = father;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getBorn() {
		return born;
	}

	public void setBorn(Date born) {
		this.born = born;
	}
}
