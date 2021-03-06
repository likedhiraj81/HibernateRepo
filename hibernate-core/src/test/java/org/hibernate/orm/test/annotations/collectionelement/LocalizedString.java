/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id$
package org.hibernate.orm.test.annotations.collectionelement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MapKeyColumn;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;

/**
 * @author Emmanuel Bernard
 */
@Embeddable
public class LocalizedString implements Serializable {

	private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	public LocalizedString() {
	}

	public LocalizedString(String string) {
		this.getVariations().put( DEFAULT_LOCALE.getLanguage(), string );
	}

	private Map<String, String> variations =
			new HashMap<String, String>( 1 );

	@ElementCollection
	@MapKeyColumn(name = "language_code" )
	@Fetch( FetchMode.JOIN )
	@Filter( name = "selectedLocale",
			condition = " language_code = :param " )
	public Map<String, String> getVariations() {
		return variations;
	}

	public void setVariations(Map<String, String> variations) {
		this.variations = variations;
	}
}
