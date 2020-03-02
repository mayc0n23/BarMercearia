package model;

import java.util.ArrayList;
import dao.IPedidoDAO;
import dao.PedidoDAO;
import dto.PedidoDTO;

public class Pedido {
	private String data;
	private float valor;
	private ArrayList<Integer> produtos = new ArrayList<>();
	private Cliente cliente;
	private IPedidoDAO dao = new PedidoDAO();
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
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
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public void addProduto(int p) {
		produtos.add(p);
	}
	public void cadastrar(PedidoDTO p) {
		dao.cadastrarPedido(p);
	}
	public void editar(PedidoDTO p) {
		dao.editarPedido(p);
	}
	public void deletar(PedidoDTO p) {
		dao.deletarPedido(p);
	}
	public PedidoDTO recuperar() {
		return dao.listarPedidos();
	}
	public void inserirPedidoProduto(int idProduto, int idPedido, int qtd) {
		dao.pedidoProduto(idProduto, idPedido, qtd);
	}
	public int recoverId() {
		return dao.recuperarId();
	}
	public PedidoDTO relatorio(String mes, int id) {
		return dao.relatorioLucro(mes, id);
	}
}