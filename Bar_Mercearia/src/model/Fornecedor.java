package model;

import dao.FornecedorDAO;
import dao.IFornecedorDAO;
import dto.FornecedorDTO;

public class Fornecedor extends Pessoa{
	private String cnpj;
	private IFornecedorDAO dao = new FornecedorDAO();

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public void cadastrar(FornecedorDTO f) throws FornecedorJaCadastradoException{
		dao.cadastrarFornecedor(f);
	}
	public void deletar(FornecedorDTO f) {
		dao.deletarFornecedor(f);
	}
	public void editar(FornecedorDTO f) {
		dao.editarFornecedor(f);
	}
	public FornecedorDTO listaDeFornecedores(){
		return dao.listarFornecedores();
	}
	public FornecedorDTO pesquisarFornecedor(String cnpj) {
		return dao.pesquisarFornecedor(cnpj);
	}
}