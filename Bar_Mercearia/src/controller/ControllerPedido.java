package controller;

import dto.PedidoDTO;
import model.Pedido;

public class ControllerPedido {
	public void cadastrar(PedidoDTO p) {
		Pedido pd = new Pedido();
		pd.cadastrar(p);
	}
	public void deletar(PedidoDTO p) {
		Pedido pd = new Pedido();
		pd.deletar(p);
	}
	public void editar(PedidoDTO p) {
		Pedido pd = new Pedido();
		pd.editar(p);
	}
	public PedidoDTO listar() {
		Pedido pd = new Pedido();
		return pd.recuperar();
	}
	public void inserirPedido(int idProduto, int idPedido, int qtd) {
		Pedido pd = new Pedido();
		pd.inserirPedidoProduto(idProduto, idPedido, qtd);
	}
	public int pegarId() {
		Pedido pd = new Pedido();
		return pd.recoverId();
	}
	public PedidoDTO pegarRelatorio(String mes, int id) {
		Pedido pd = new Pedido();
		return pd.relatorio(mes, id);
	}
}