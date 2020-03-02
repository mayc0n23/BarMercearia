package dto;

import java.sql.Timestamp;
import java.util.ArrayList;

public class PagamentoDTO implements DTO{
	private Timestamp dataHora = new Timestamp(System.currentTimeMillis());
	private int vendedor;
	private ArrayList<Object> todosPagamentos = new ArrayList<>();
	private float valor;
	private int cliente;
	private String nomeCliente;
	private String nomeVendedor;
	
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getNomeVendedor() {
		return nomeVendedor;
	}
	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}
	public int getVendedor() {
		return vendedor;
	}
	public void setVendedor(int vendedor) {
		this.vendedor = vendedor;
	}
	public void addPagamento(PagamentoDTO p) {
		todosPagamentos.add(p);
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public int getCliente() {
		return cliente;
	}
	public void setCliente(int cliente) {
		this.cliente = cliente;
	}
	public void setDataHora(Timestamp t) {
		dataHora = t;
	}
	public Timestamp getDataHora() {
		return dataHora;
	}
	public ArrayList<Object> getTodosPagamentos() {
		return todosPagamentos;
	}
}