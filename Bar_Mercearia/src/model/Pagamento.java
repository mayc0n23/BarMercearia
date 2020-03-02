package model;

import dao.IPagamentoDAO;
import dao.PagamentoDAO;
import dto.PagamentoDTO;

public class Pagamento {
	private String hora;
	private String data;
	private Vendedor vendedor;
	private float valor;
	private Cliente cliente;
	private IPagamentoDAO dao = new PagamentoDAO();
	
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Vendedor getVendedor() {
		return vendedor;
	}
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void pagar(PagamentoDTO p) {
		dao.pagar(p);
	}
	public PagamentoDTO listarPagamentos() {
		return dao.listarPagamentos();
	}
}