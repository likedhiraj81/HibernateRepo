/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.envers.integration.interfaces.hbm.propertiesAudited2;

import org.hibernate.envers.Audited;

/**
 * @author Hern�n Chanfreau
 */
public interface SimpleInterface {

	long getId();

	void setId(long id);

	@Audited
	String getData();

	void setData(String data);

	@Audited
	int getNumerito();

	void setNumerito(int num);

}
