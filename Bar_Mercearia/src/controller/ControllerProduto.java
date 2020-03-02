package controller;

import dto.ProdutoDTO;
import model.Produto;

public class ControllerProduto {
	public void cadastrar(ProdutoDTO p) {
		Produto pr = new Produto();
		pr.cadastrar(p);
	}
	public void editar(ProdutoDTO p) {
		Produto pr = new Produto();
		pr.editar(p);
	}
	public void deletar(ProdutoDTO p) {
		Produto pr = new Produto();
		pr.deletar(p);
	}
	public ProdutoDTO listar() {
		Produto pr = new Produto();
		return pr.listar();
	}
	public ProdutoDTO buscar(String nome) {
		Produto pr = new Produto();
		return pr.buscar(nome);
	}
	public void inserirLote(int id, String data) {
		Produto pr = new Produto();
		pr.lote(id, data);
	}
	public void inserirCompraProduto(int idProduto, int idForn, float precoCompra, String dataCompra, int qtd) {
		Produto pr = new Produto();
		pr.compraProd(idProduto, idForn, precoCompra, dataCompra, qtd);
	}
	public ProdutoDTO produtosEsgotados() {
		Produto pr = new Produto();
		return pr.relatorioProdEsgotado();
	}
	public ProdutoDTO produtosEstoque() {
		Produto pr = new Produto();
		return pr.relatorioProdEstoque();
	}
}