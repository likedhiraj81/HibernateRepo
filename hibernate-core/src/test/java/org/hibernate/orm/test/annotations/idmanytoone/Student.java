/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.annotations.idmanytoone;

import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author Alex Kalashnikov
 */
@Entity
@Table(name = "idmanytoone_student")
public class Student implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany(mappedBy = "student")
    private Set<CourseStudent> courses;

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CourseStudent> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseStudent> courses) {
        this.courses = courses;
    }
}
