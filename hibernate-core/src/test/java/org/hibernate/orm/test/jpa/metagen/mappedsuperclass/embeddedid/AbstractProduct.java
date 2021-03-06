/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.jpa.metagen.mappedsuperclass.embeddedid;

import java.io.Serializable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;

/**
 * @author Justin Wesley
 * @author Steve Ebersole
 */
@MappedSuperclass
public class AbstractProduct implements Serializable {
	private ProductId id;

	public AbstractProduct() {
	}

	@EmbeddedId
	public ProductId getId() {
		return id;
	}

	public void setId(ProductId id) {
		this.id = id;
	}
}
