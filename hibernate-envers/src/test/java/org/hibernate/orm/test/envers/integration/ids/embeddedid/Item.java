/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.orm.test.envers.integration.ids.embeddedid;

import java.io.Serializable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import org.hibernate.envers.Audited;

/**
 * @author Lukasz Antoniak (lukasz dot antoniak at gmail dot com)
 */
@Entity
@Audited
public class Item implements Serializable {
	@EmbeddedId
	private ItemId id;

	private Double price;

	public Item() {
	}

	public Item(ItemId id, Double price) {
		this.id = id;
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof Item) ) {
			return false;
		}

		Item item = (Item) o;

		if ( getId() != null ? !getId().equals( item.getId() ) : item.getId() != null ) {
			return false;
		}
		if ( getPrice() != null ? !getPrice().equals( item.getPrice() ) : item.getPrice() != null ) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (price != null ? price.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Item(id = " + id + ", price = + " + price + ")";
	}

	public ItemId getId() {
		return id;
	}

	public void setId(ItemId id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
