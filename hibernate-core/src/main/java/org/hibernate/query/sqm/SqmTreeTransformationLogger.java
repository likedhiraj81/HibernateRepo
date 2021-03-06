/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.query.sqm;

import org.hibernate.query.QueryLogging;

import org.jboss.logging.Logger;

/**
 * @author Steve Ebersole
 */
public interface SqmTreeTransformationLogger {
	String LOGGER_NAME = QueryLogging.subLoggerName( "sqm.transform" );

	Logger LOGGER = Logger.getLogger( LOGGER_NAME );

	boolean TRACE_ENABLED = LOGGER.isTraceEnabled();
	boolean DEBUG_ENABLED = LOGGER.isDebugEnabled();
}
