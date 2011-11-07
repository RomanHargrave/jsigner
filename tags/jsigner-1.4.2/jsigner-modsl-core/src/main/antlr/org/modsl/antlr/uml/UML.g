grammar UML;

options {
	language = Java;
}

@lexer::header {
	package org.modsl.antlr.uml;
}

@parser::header {
	package org.modsl.antlr.uml;
	import org.modsl.core.agt.model.*;
	import org.modsl.core.agt.visitor.*;
	import org.modsl.core.lang.uml.*;
	import org.modsl.core.lang.uml.factory.*;
	import java.util.LinkedList;
}

@parser::members {
	public Graph graph;
	protected LinkedList<Graph> nodes = new LinkedList<Graph>();
	protected UMLCollabFactory collabFactory = new UMLCollabFactory();
    protected UMLClassFactory classFactory = new UMLClassFactory();
    protected UMLSeqFactory seqFactory = new UMLSeqFactory();
	protected List<NodeLabel> curElements = new LinkedList<NodeLabel>();
	protected LinkedList<String> collabEdges = new LinkedList<String>();
	protected LinkedList<String> seqEdges = new LinkedList<String>();
	protected LinkedList<String> curAggs = new LinkedList<String>();
}

diagram : classDiagram | collabDiagram | seqDiagram;

collabDiagram 
	@init { graph = collabFactory.createGraph();  }
	@after { graph.accept(new NodeRefVisitor()); }
	: ('collab' | 'collaboration' | 'communication') 'diagram'? ID procAttributes? '{' collabStmt* '}' { graph.setName($ID.text); };

collabStmt: objInstance collab2Stmt+ ';'  
	{ collabEdges.addFirst($objInstance.text); collabFactory.createEdges(graph, collabEdges); collabEdges.clear(); };

collab2Stmt: EDGEOP objInstance '.' method
	{ collabEdges.add($method.text); collabEdges.add($objInstance.text); };

seqDiagram
    @init { graph = seqFactory.createGraph(); }
    @after { graph.accept(new NodeRefVisitor()); }
    : ('sequence' | 'seq') 'diagram'? ID procAttributes? '{' seqStmt* '}' { graph.setName($ID.text); };

seqStmt: objInstance seq2Stmt+ ';'  
	{ seqEdges.addFirst($objInstance.text); seqFactory.createEdges(graph, seqEdges); seqEdges.clear(); };

seq2Stmt: EDGEOP objInstance '.' method
	{ seqEdges.add($method.text); seqEdges.add($objInstance.text); };

classDiagram 
	@init { graph = classFactory.createGraph(); }
	@after { graph.accept(new NodeRefVisitor()); }
	: 'class' 'diagram'? ID procAttributes? '{' (classStmt | interfaceStmt)* '}' { graph.setName($ID.text); };

classStmt: (abs='abstract')? 'class' id=ID ('<' gid+=ID (',' gid+=ID)* '>')?
	('extends' eid+=ID (',' eid+=ID)*)? ('implements' iid+=ID (',' iid+=ID)*)? 
	'{' classElementStmt* '}'
	{ 	
		classFactory.createClassNode(abs, graph, $id, $gid, curElements, $eid, $iid, curAggs); 
		curElements.clear(); 
		curAggs.clear(); 
	}; 

interfaceStmt: 'interface' id=ID ('<' gid+=ID (',' gid+=ID)* '>')? 
	('extends' eid+=ID (',' eid+=ID)*)? '{' interfaceElementStmt* '}'  
	{ classFactory.createInterfaceNode(graph, $id, $gid, curElements, $eid); curElements.clear(); }; 

classElementStmt: varClassElementStmt | staticVarClassElementStmt 
	| methodClassElementStmt | abstractMethodClassElementStmt | staticMethodClassElementStmt | aggStmt;

interfaceElementStmt: methodClassElementStmt | staticMethodClassElementStmt;

varClassElementStmt: var ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_VAR_NODE_LABEL, $var.text)); };

staticVarClassElementStmt: 'static' var ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_STATIC_VAR_NODE_LABEL, $var.text)); };

methodClassElementStmt: method ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_METHOD_NODE_LABEL, $method.text)); };

abstractMethodClassElementStmt: 'abstract' method ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_ABSTRACT_METHOD_NODE_LABEL, $method.text)); };

staticMethodClassElementStmt: 'static' method ';' 
	{ curElements.add(classFactory.createNodeElement(UMLMetaType.CLASS_STATIC_METHOD_NODE_LABEL, $method.text)); };

aggStmt: from=multiplicity EDGEOP to=multiplicity '(' ID ')' ';'
	{ curAggs.add($from.text); curAggs.add($to.text); curAggs.add($ID.text); };

procAttributes: '(' procAttr (',' procAttr)* ')';

procAttr: key=ID ':' (v=INT | v=STRING) { graph.addProcAttr($key.text, $v.text); };

var: ('-' | '+' | '#' )? ID (':' ID)?;

objInstance: ID? ':' ID | ID;

method: ('-' | '+' | '#' )? ID '(' (ID (',' ID)*)? ')' (':' ID)?;

multiplicity: multibound ('..' multibound)?;

multibound: '*' | ID | INT;

ML_COMMENT: '/*' .* '*/' { skip(); };
SL_COMMENT: '//' .* NEWLINE { skip(); };
STRING: '"' .* '"';

EDGEOP: '->';
ID: ('_' | 'a'..'z' | 'A'..'Z' | 'À'..'ÿ') (INT | '_' | 'a'..'z' |'A'..'Z' | 'À'..'ÿ' | '[' | ']')*;
INT : '0'..'9'+ ;
WS: (' ' | '\t' | NEWLINE)+ { skip(); };
fragment NEWLINE:'\r'? '\n';
