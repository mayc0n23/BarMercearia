package dto;

import java.sql.Date;
import java.util.ArrayList;

public class PedidoDTO implements DTO{
	private int id;
	private java.sql.Date data = new Date(new java.util.Date().getTime());
	private float valor;
	private ArrayList<Integer> produtos = new ArrayList<>();
	private ClienteDTO cliente;
	private ArrayList<Object> todosPedidos = new ArrayList<>();
	private VendedorDTO vendedor;
	private ArrayList<ArrayList<Object>> relatorio = new ArrayList<>();
	
	public ArrayList<ArrayList<Object>> getRelatorio() {
		return relatorio;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public VendedorDTO getVendedor() {
		return vendedor;
	}
	public void setVendedor(VendedorDTO vendedor) {
		this.vendedor = vendedor;
	}
	public java.sql.Date getData() {
		return data;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public ArrayList<Integer> getProdutos() {
		return produtos;
	}
	public void setProdutos(ArrayList<Integer> produtos) {
		this.produtos = produtos;
	}
	public ClienteDTO getCliente() {
		return cliente;
	}
	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}
	public void setData(java.sql.Date data) {
		this.data = data;
	}
	public ArrayList<Object> getTodosPedidos() {
		return todosPedidos;
	}
	public void addProduto(int p) {
		produtos.add(p);
	}
	public void addPedido(PedidoDTO p) {
		todosPedidos.add(p);
	}
}