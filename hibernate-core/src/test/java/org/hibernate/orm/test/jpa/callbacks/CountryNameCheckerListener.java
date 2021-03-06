/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id$
package org.hibernate.orm.test.jpa.callbacks;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import org.hibernate.orm.test.jpa.callbacks.Translation;

/**
 * @author Emmanuel Bernard
 */
public class CountryNameCheckerListener {
	private List<String> countries = new ArrayList<String>();

	{
		countries.add( "France" );
		countries.add( "Netherland" );
	}

	@PrePersist
	@PreUpdate
	public void testCountryName(Object object) {
		if ( object instanceof Translation ) {
			Translation tr = (Translation) object;
			if ( ! countries.contains( tr.getInto() ) ) {
				throw new IllegalArgumentException( "Not a country name: " + tr.getInto() );
			}
		}
	}
}
