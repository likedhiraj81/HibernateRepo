/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.envers.integration.interfaces.hbm.propertiesAudited2;


/**
 * @author Hern�n Chanfreau
 */
public class NonAuditedImplementor implements SimpleInterface {
	private long id;
	private String data;
	private String nonAuditedImplementorData;
	private int numerito;

	protected NonAuditedImplementor() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNonAuditedImplementorData() {
		return nonAuditedImplementorData;
	}

	public void setNonAuditedImplementorData(String implementorData) {
		this.nonAuditedImplementorData = implementorData;
	}

	public int getNumerito() {
		return numerito;
	}

	public void setNumerito(int numerito) {
		this.numerito = numerito;
	}
}
