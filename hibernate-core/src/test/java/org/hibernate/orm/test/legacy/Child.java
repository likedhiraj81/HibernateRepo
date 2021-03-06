/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id: Child.java 4599 2004-09-26 05:18:27Z oneovthafew $
package org.hibernate.orm.test.legacy;


public class Child {
	private Parent parent;
	private int count;
	private int x;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public Parent getParent() {
		return parent;
	}
	
	
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	
	
	public int getCount() {
		return count;
	}
	
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public long getId() {
		return parent.getId();
	}
	private void setId(long id) {
	}
	
}







