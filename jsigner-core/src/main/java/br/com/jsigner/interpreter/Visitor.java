package br.com.jsigner.interpreter;

public interface Visitor<T, R> {

	public void visit(T object);
	
	public R getResult();

}
