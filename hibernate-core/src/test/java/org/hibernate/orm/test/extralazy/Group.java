/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id: Group.java 7628 2005-07-24 06:55:01Z oneovthafew $
package org.hibernate.orm.test.extralazy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gavin King
 */
public class Group {
	private String name;
	private Map users = new HashMap();
	Group() {}
	public Group(String n) {
		name = n;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map getUsers() {
		return users;
	}
	public void setUsers(Map users) {
		this.users = users;
	}
}
