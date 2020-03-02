package view;

import java.util.ArrayList;

public class IteratorOfLists implements Iterator{
	private ArrayList<Object> lista;
	private int contador;
	
	public IteratorOfLists(ArrayList<Object> lista) {
		this.lista = lista;
	}

	@Override
	public void first() { //inicia meu iterator
		contador = 0;
	}

	@Override
	public void next() { //incrementa o iterator
		contador++;
	}

	@Override
	public boolean isDone() { //verifica se o percurso já encerrou
		return (contador == lista.size());
	}

	@Override
	public Object currentItem() { //retorna o objeto atual
		if(isDone()) {
			contador = lista.size()-1;
		}else if(contador < 0) {
			contador = 0;
		}
		return lista.get(contador);
	}
}