package view;

import java.util.ArrayList;
import dto.ProdutoDTO;

public class Mesa {
	private ArrayList<ArrayList<Object>> produtos = new ArrayList<>();
	public void setProdutos(ArrayList<ArrayList<Object>> produtos) {
		this.produtos = produtos;
	}
	private String status;
	private int numMesa;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getNumMesa() {
		return numMesa;
	}
	public void setNumMesa(int numMesa) {
		this.numMesa = numMesa;
	}
	public ArrayList<ArrayList<Object>> getProdutos() {
		return produtos;
	}
	public void add(ArrayList<Object> p) {
		produtos.add(p);
	}
}