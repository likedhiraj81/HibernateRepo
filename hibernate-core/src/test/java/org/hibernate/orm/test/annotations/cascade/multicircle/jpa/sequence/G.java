/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.annotations.cascade.multicircle.jpa.sequence;

/**
 * No Documentation
 */
@jakarta.persistence.Entity
public class G extends AbstractEntity {
    private static final long serialVersionUID = 325417437L;

	@jakarta.persistence.ManyToOne(optional = false)
	private B b;

    @jakarta.persistence.OneToMany(mappedBy = "g")
    private java.util.Set<F> fCollection = new java.util.HashSet<F>();

	public B getB() {
		return b;
	}
	public void setB(B parameter){
		this.b = parameter;
	}

    public java.util.Set<F> getFCollection() {
        return fCollection;
    }
    public void setFCollection(
        java.util.Set<F> parameter) {
        this.fCollection = parameter;
    }
}
