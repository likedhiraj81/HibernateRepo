/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id: Child.java 6978 2005-06-01 03:29:27Z oneovthafew $
package org.hibernate.orm.test.compositeelement;


/**
 * @author gavin
 */
public class Child {
	private String name;
	private String bio;
	private Parent parent;
	private int bioLength;
	private int position;

	Child() {}
	public Child(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the parent.
	 */
	public Parent getParent() {
		return parent;
	}
	/**
	 * @param parent The parent to set.
	 */
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int hashCode() {
		return name.hashCode();
	}
	public boolean equals(Object other) {
		Child c = (Child) other;
		return c.parent.getId().equals(parent.getId()) 
			&& c.name.equals(name);
	}
	public int getBioLength() {
		return bioLength;
	}
	public void setBioLength(Integer bioLength) {
		this.bioLength = bioLength==null ? 0 : bioLength.intValue();
	}
}
