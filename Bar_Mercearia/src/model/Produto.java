package model;

import dao.IProdutoDAO;
import dao.ProdutoDAO;
import dto.ProdutoDTO;

public class Produto {
	private String nome;
	private int qntdEstoque;
	private String unidadeMedida;
	private float precoUnidade;
	private IProdutoDAO dao = new ProdutoDAO();
	
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
	public void cadastrar(ProdutoDTO p) {
		dao.cadastrarProduto(p);
	}
	public void deletar(ProdutoDTO p) {
		dao.deletarProduto(p);
	}
	public void editar(ProdutoDTO p) {
		dao.editarProduto(p);
	}
	public ProdutoDTO listar() {
		return dao.listarProdutos();
	}
	public ProdutoDTO buscar(String nome) {
		return dao.buscarProduto(nome);
	}
	public void lote(int id, String data) {
		dao.cadastrarLote(id, data);
	}
	public void compraProd(int idProduto, int idForn, float precoCompra, String dataCompra, int qtd) {
		dao.compraProduto(idProduto, idForn, precoCompra, dataCompra, qtd);
	}
	public ProdutoDTO relatorioProdEsgotado() {
		return dao.produtosEsgotados();
	}
	public ProdutoDTO relatorioProdEstoque() {
		return dao.produtosEstoque();
	}
}