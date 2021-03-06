/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id$
package org.hibernate.orm.test.annotations.tableperclass;

import jakarta.persistence.Entity;

/**
 * @author Emmanuel Bernard
 */
@Entity
public class T800 extends Robot {
	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	private String targetName;
}
