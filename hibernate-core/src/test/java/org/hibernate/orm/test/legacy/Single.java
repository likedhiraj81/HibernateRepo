/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id: Single.java 4599 2004-09-26 05:18:27Z oneovthafew $
package org.hibernate.orm.test.legacy;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;


public class Single implements Serializable {
	private String id;
	private String prop;
	private String string;
	private Collection several = new HashSet();
	/**
	 * Returns the id.
	 * @return String
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Returns the prop.
	 * @return String
	 */
	public String getProp() {
		return prop;
	}
	
	/**
	 * Returns the several.
	 * @return Set
	 */
	public Collection getSeveral() {
		return several;
	}
	
	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Sets the prop.
	 * @param prop The prop to set
	 */
	public void setProp(String prop) {
		this.prop = prop;
	}
	
	/**
	 * Sets the several.
	 * @param several The several to set
	 */
	public void setSeveral(Collection several) {
		this.several = several;
	}
	
	/**
	 * Returns the string.
	 * @return String
	 */
	public String getString() {
		return string;
	}
	
	/**
	 * Sets the string.
	 * @param string The string to set
	 */
	public void setString(String string) {
		this.string = string;
	}
	
	/*public boolean equals(Object other) {
		if ( !(other instanceof Single) ) return false;
		return ( (Single) other ).id.equals(id) && ( (Single) other ).string.equals(string);
	}
	
	public int hashCode() {
		return id.hashCode();
	}*/
	
}






