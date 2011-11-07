grammar Basic;

options {
	language = Java;
}

@lexer::header {
	package org.modsl.antlr.basic;
}

@parser::header {
	package org.modsl.antlr.basic;
	import org.modsl.core.agt.model.*;
	import org.modsl.core.agt.visitor.*;
	import org.modsl.core.lang.basic.*;
	import java.util.LinkedList;
}

@parser::members {
	public Graph graph;
	protected BasicFactory factory = new BasicFactory();
}

graph 
	@init{ graph = factory.createGraph(); }
	@after { graph.accept(new NodeRefVisitor()); }
	: 'graph' ID procAttributes? '{' statement* '}' { graph.setName($ID.text); };

procAttributes: '(' procAttr (',' procAttr)* ')';

procAttr: key=ID ':' v=INT { graph.addProcAttr($key.text, $v.text); };

statement: (nodeStatement | edgeStatement) ';';

nodeStatement : ID { Node n = factory.createNode(graph, $ID);  };

edgeStatement: ids+=ID EDGEOP ids+=ID (EDGEOP ids+=ID)* { factory.createEdges(graph, $ids); };

EDGEOP: '->' | '--';
ID: '"' .* '"' |  ('_' | 'a'..'z' |'A'..'Z' | 'À'..'ÿ') (INT | '_' | 'a'..'z' |'A'..'Z' | 'À'..'ÿ')* ;
INT : '0'..'9'+ ;
NEWLINE:'\r'? '\n';
WS: (' ' |'\t' | '\r' | '\n')+ { skip(); };