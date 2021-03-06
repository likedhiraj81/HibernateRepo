/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id: Country.java 6736 2005-05-09 16:09:38Z epbernard $
package org.hibernate.orm.test.stats;


/**
 * @author Emmanuel Bernard
 */
public class Country {
	private Integer id;
	private String name;

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Country)) return false;

		final Country country = (Country) o;

		if (!name.equals(country.name)) return false;

		return true;
	}

	public int hashCode() {
		return name.hashCode();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
