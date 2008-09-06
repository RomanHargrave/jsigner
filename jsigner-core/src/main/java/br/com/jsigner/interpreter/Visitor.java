package br.com.jsigner.interpreter;

public interface Visitor<K> {
	
	public void visit(K object);

}
