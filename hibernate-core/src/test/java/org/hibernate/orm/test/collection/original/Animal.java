/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.collection.original;


public class Animal {
    long id;
    String name;
    boolean boolvar;
    Zoo zoo;

    public long getId() {
        return id;
    }
    public void setId( long id ) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName( String name ) {
        this.name = name;
    }
    public boolean isBoolvar() {
		return boolvar;
	}
	public void setBoolvar(boolean boolvar) {
		this.boolvar = boolvar;
	}
	public Zoo getZoo() {
		return zoo;
	}
	public void setZoo(Zoo zoo) {
		this.zoo = zoo;
	}
}
