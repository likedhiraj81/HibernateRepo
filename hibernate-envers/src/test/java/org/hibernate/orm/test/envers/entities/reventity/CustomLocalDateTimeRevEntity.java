/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.envers.test.entities.reventity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

/**
 * @author Chris Cranford
 */
@Entity
@GenericGenerator(name = "EnversTestingRevisionGenerator",
        strategy = "org.hibernate.id.enhanced.TableGenerator",
        parameters = {
                @Parameter(name = "table_name", value = "REVISION_GENERATOR"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1"),
                @Parameter(name = "prefer_entity_table_as_segment_value", value = "true")
        }
)
@RevisionEntity
public class CustomLocalDateTimeRevEntity {
    @Id
    @GeneratedValue(generator = "EnversTestingRevisionGenerator")
    @RevisionNumber
    private int id;

    @RevisionTimestamp
    private LocalDateTime localDateTimestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getLocalDateTimestamp() {
        return localDateTimestamp;
    }

    public void setLocalDateTimestamp(LocalDateTime localDateTimestamp) {
        this.localDateTimestamp = localDateTimestamp;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + ( localDateTimestamp != null ? localDateTimestamp.hashCode() : 0 );
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomLocalDateTimeRevEntity that = (CustomLocalDateTimeRevEntity) o;
        return id == that.id && Objects.equals(localDateTimestamp, that.localDateTimestamp);
    }
}
