/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.jpa.cascade.multilevel;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_TOP")
public class Top {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "top")
    List<Middle> middles;

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    List<Middle> getMiddles() {
        if (middles == null) {
            middles = new ArrayList<Middle>();
        }
        return middles;
    }

    void setMiddles(List<Middle> middles) {
        this.middles = middles;
    }

    void addMiddle(Middle middle) {
        this.getMiddles().add(middle);
        middle.setTop(this);
    }
}
