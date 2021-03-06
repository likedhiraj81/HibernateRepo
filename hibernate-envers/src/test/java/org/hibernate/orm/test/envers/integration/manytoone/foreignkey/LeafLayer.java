/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.envers.integration.manytoone.foreignkey;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

import org.hibernate.envers.Audited;

/**
 * @author Chris Cranford
 */
@Entity(name = "LeafLayer")
@Audited
public class LeafLayer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(optional = false)
	@JoinColumns({
			@JoinColumn(name = "middle_layer_valid_from_fk", referencedColumnName = "valid_from"),
			@JoinColumn(name = "middle_layer_root_layer_fk", referencedColumnName = "root_layer_fk") })
	private MiddleLayer middleLayer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MiddleLayer getMiddleLayer() {
		return middleLayer;
	}

	public void setMiddleLayer(MiddleLayer middleLayer) {
		this.middleLayer = middleLayer;
	}
}
