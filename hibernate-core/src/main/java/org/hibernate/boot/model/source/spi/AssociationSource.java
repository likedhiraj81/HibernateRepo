/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.boot.model.source.spi;

/**
 * @author Gail Badner
 */
public interface AssociationSource {

	AttributeSource getAttributeSource();

	/**
	 * Obtain the name of the referenced entity.
	 *
	 * @return The name of the referenced entity
	 */
	String getReferencedEntityName();

	boolean isIgnoreNotFound();

	boolean isMappedBy();
}
