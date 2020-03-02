package dto;

import java.util.ArrayList;

public class ProdutoDTO implements DTO{
	private int id;
	private String nome;
	private int qntdEstoque;
	private String unidadeMedida;
	private float precoUnidade;
	private float precoCompra;
	private ArrayList<Object> listaProdutos = new ArrayList<>();
	private ArrayList<ArrayList<Object>> relatorio = new ArrayList<>();
	
	public ArrayList<ArrayList<Object>> getRelatorio() {
		return relatorio;
	}
	public ArrayList<Object> getListaProdutos() {
		return listaProdutos;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getQntdEstoque() {
		return qntdEstoque;
	}
	public void setQntdEstoque(int qntdEstoque) {
		this.qntdEstoque = qntdEstoque;
	}
	public String getUnidadeMedida() {
		return unidadeMedida;
	}
	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
	public float getPrecoUnidade() {
		return precoUnidade;
	}
	public void setPrecoUnidade(float precoUnidade) {
		this.precoUnidade = precoUnidade;
	}
	public float getPrecoCompra() {
		return precoCompra;
	}
	public void setPrecoCompra(float precoCompra) {
		this.precoCompra = precoCompra;
	}
	public void addProduto(ProdutoDTO p) {
		listaProdutos.add(p);
	}
}