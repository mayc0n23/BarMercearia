package dao;

import dto.ProdutoDTO;

public interface IProdutoDAO {
	public void cadastrarProduto(ProdutoDTO p);
	public void deletarProduto(ProdutoDTO p);
	public void editarProduto(ProdutoDTO p);
	public ProdutoDTO listarProdutos();
	public ProdutoDTO buscarProduto(String nome);
	public void cadastrarLote(int id, String data);
	public void compraProduto(int idProduto, int idForn, float precoCompra, String dataCompra, int qtd);
	public ProdutoDTO produtosEsgotados();
	public ProdutoDTO produtosEstoque();
}