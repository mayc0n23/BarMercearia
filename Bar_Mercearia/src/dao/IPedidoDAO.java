package dao;

import dto.PedidoDTO;

public interface IPedidoDAO {
	public void cadastrarPedido(PedidoDTO p);
	public void deletarPedido(PedidoDTO p);
	public void editarPedido(PedidoDTO p);
	public PedidoDTO listarPedidos();
	public void pedidoProduto(int idProduto, int idPedido, int qtd);
	public int recuperarId();
	public PedidoDTO relatorioLucro(String mes, int id);
}