parser grammar GraphLanguageParser;

options {
	tokenVocab=GraphLanguageLexer;
}

@header {
/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.grammars.graph;
}

@members {
/*
 * Antlr grammar describing the Hibernate EntityGraph Language - for parsing a structured
 * textual representation of an entity graph
 *
 * `GraphLanguageParser.g4`
 */
}


graph
 	: attributeList
 	;

attributeList
	: attributeNode (COMMA attributeNode)*
    ;

attributeNode
	: attributePath subGraph?
	;

attributePath
	: ATTR_NAME attributeQualifier?
	;

attributeQualifier
	: DOT ATTR_NAME
	;

subGraph
	: LPAREN (subType COLON)? attributeList RPAREN
	;

subType
	: TYPE_NAME
	;
