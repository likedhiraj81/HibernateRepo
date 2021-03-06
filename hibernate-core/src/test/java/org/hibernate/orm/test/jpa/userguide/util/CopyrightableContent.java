/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.jpa.userguide.util;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;


/**
 * @author Emmanuel Bernard
 */
@MappedSuperclass
public abstract class CopyrightableContent {
	private Author author;

	public CopyrightableContent() {
	}

	public CopyrightableContent(Author author) {
		this.author = author;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	private Author getAuthor() {
		return author;
	}

	private void setAuthor(Author author) {
		this.author = author;
	}
}
