/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id$
package org.hibernate.orm.test.annotations.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

/**
 * @author Emmanuel Bernard
 */
@Entity
public class Bid {
	private Integer id;
	private String description;
	private Starred note;
	private Starred editorsNote;
	private Boolean approved;

	@Enumerated(EnumType.STRING)
	//@Column(columnDefinition = "VARCHAR(10)")
	public Starred getEditorsNote() {
		return editorsNote;
	}

	public void setEditorsNote(Starred editorsNote) {
		this.editorsNote = editorsNote;
	}

	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Starred getNote() {
		return note;
	}

	public void setNote(Starred note) {
		this.note = note;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

}
