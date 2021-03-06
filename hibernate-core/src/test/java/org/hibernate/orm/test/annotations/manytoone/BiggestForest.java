/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id$
package org.hibernate.orm.test.annotations.manytoone;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 * @author Emmanuel Bernard
 */
@Entity
public class BiggestForest {
	private Integer id;
	private ForestType type;

	@Id @GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(mappedBy = "biggestRepresentative")
	public ForestType getType() {
		return type;
	}

	public void setType(ForestType type) {
		this.type = type;
	}
}
