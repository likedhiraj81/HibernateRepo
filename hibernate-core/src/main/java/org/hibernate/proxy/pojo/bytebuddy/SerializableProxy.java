/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.proxy.pojo.bytebuddy;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;
import org.hibernate.bytecode.spi.BytecodeProvider;
import org.hibernate.cfg.Environment;
import org.hibernate.proxy.AbstractSerializableProxy;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.CompositeType;

public final class SerializableProxy extends AbstractSerializableProxy {
	private final Class<?> persistentClass;
	private final Class<?>[] interfaces;

	private final String identifierGetterMethodName;
	private final Class<?> identifierGetterMethodClass;

	private final String identifierSetterMethodName;
	private final Class<?> identifierSetterMethodClass;
	private final Class<?>[] identifierSetterMethodParams;

	private final CompositeType componentIdType;

	public SerializableProxy(
			String entityName,
			Class<?> persistentClass,
			Class<?>[] interfaces,
			Object id,
			Boolean readOnly,
			String sessionFactoryUuid,
			boolean allowLoadOutsideTransaction,
			Method getIdentifierMethod,
			Method setIdentifierMethod,
			CompositeType componentIdType) {
		super( entityName, id, readOnly, sessionFactoryUuid, allowLoadOutsideTransaction );
		this.persistentClass = persistentClass;
		this.interfaces = interfaces;
		if ( getIdentifierMethod != null ) {
			identifierGetterMethodName = getIdentifierMethod.getName();
			identifierGetterMethodClass = getIdentifierMethod.getDeclaringClass();
		}
		else {
			identifierGetterMethodName = null;
			identifierGetterMethodClass = null;
		}

		if ( setIdentifierMethod != null ) {
			identifierSetterMethodName = setIdentifierMethod.getName();
			identifierSetterMethodClass = setIdentifierMethod.getDeclaringClass();
			identifierSetterMethodParams = setIdentifierMethod.getParameterTypes();
		}
		else {
			identifierSetterMethodName = null;
			identifierSetterMethodClass = null;
			identifierSetterMethodParams = null;
		}

		this.componentIdType = componentIdType;
	}

	@Override
	protected String getEntityName() {
		return super.getEntityName();
	}

	@Override
	protected Object getId() {
		return super.getId();
	}

	Class<?> getPersistentClass() {
		return persistentClass;
	}

	Class<?>[] getInterfaces() {
		return interfaces;
	}

	String getIdentifierGetterMethodName() {
		return identifierGetterMethodName;
	}

	Class<?> getIdentifierGetterMethodClass() {
		return identifierGetterMethodClass;
	}

	String getIdentifierSetterMethodName() {
		return identifierSetterMethodName;
	}

	Class<?> getIdentifierSetterMethodClass() {
		return identifierSetterMethodClass;
	}

	Class<?>[] getIdentifierSetterMethodParams() {
		return identifierSetterMethodParams;
	}

	CompositeType getComponentIdType() {
		return componentIdType;
	}

	private Object readResolve() {
		BytecodeProvider bytecodeProvider = Environment.getBytecodeProvider();
		if ( !( bytecodeProvider instanceof BytecodeProviderImpl ) ) {
			throw new IllegalStateException( "The bytecode provider is not ByteBuddy, unable to deserialize a ByteBuddy proxy." );
		}

		HibernateProxy proxy = ( (BytecodeProviderImpl) bytecodeProvider ).getByteBuddyProxyHelper().deserializeProxy( this );
		afterDeserialization( (ByteBuddyInterceptor) proxy.getHibernateLazyInitializer() );
		return proxy;
	}
}
