/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.envers.integration.manytoone.foreignkey;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import org.hibernate.envers.Audited;

/**
 * @author Chris Cranford
 */
@Audited
@Entity
@IdClass(MiddleLayerPK.class)
public class MiddleLayer {
	@Id
	@Column(name = "valid_from", nullable = false)
	private LocalDate validFrom;
	@Id
	@ManyToOne
	@JoinColumn(name = "root_layer_fk")
	private RootLayer rootLayer;
	@OneToMany(mappedBy = "middleLayer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LeafLayer> leafLayers;

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public RootLayer getRootLayer() {
		return rootLayer;
	}

	public void setRootLayer(RootLayer rootLayer) {
		this.rootLayer = rootLayer;
	}

	public List<LeafLayer> getLeafLayers() {
		return leafLayers;
	}

	public void setLeafLayers(List<LeafLayer> leafLayers) {
		this.leafLayers = leafLayers;
	}
}
