/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

package org.hibernate.spatial.testing.domain;

/**
 * Created by Karel Maesen, Geovise BVBA on 15/02/2018.
 */
public interface GeomEntityLike<G> {

	Integer getId();

	void setId(Integer id);

	String getType();

	void setType(String type);

	G getGeom();

	void setGeom(G geom);

	void setGeomFromWkt(String wkt);
}
