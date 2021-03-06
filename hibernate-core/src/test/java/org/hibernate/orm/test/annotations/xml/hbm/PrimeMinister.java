/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id:PrimeMinister.java 9793 2006-04-26 02:20:18 -0400 (mer., 26 avr. 2006) epbernard $
package org.hibernate.orm.test.annotations.xml.hbm;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * @author Emmanuel Bernard
 */
@Entity
public class PrimeMinister {
	private Integer id;
	private String name;
	private Government currentGovernment;
	private Set<Government> governments;

	@ManyToOne
	public Government getCurrentGovernment() {
		return currentGovernment;
	}

	public void setCurrentGovernment(Government currentGovernment) {
		this.currentGovernment = currentGovernment;
	}

	@Id
	@GeneratedValue
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

	@OneToMany(mappedBy = "primeMinister")
	public Set<Government> getGovernments() {
		return governments;
	}

	public void setGovernments(Set<Government> governments) {
		this.governments = governments;
	}

}
